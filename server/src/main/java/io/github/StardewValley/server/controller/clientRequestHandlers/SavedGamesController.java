package io.github.StardewValley.server.controller.clientRequestHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.server.JwtService;
import io.github.StardewValley.server.model.ConnectionMonitor;
import io.github.StardewValley.server.model.GameSave;
import io.github.StardewValley.server.model.GameSaveService;
import io.github.StardewValley.server.repository.GameSaveRepository;
import io.github.StardewValley.shared.dto.GameLoadStatus;
import io.github.StardewValley.shared.dto.ReadyRequest;
import io.github.StardewValley.shared.dto.SavedGameInfo;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.saveClasses.FullGameDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/savedGames")
public class SavedGamesController {

    private final JwtService jwtService;
    private final GameSaveRepository gameSaveRepository;
    private final GameSaveService gameSaveService;
    private final ObjectMapper objectMapper;
    private final HashMap<UUID, Set<String>> readyPlayers = new HashMap<>();

    public SavedGamesController(JwtService jwtService, GameSaveRepository gameSaveRepository) {
        this.jwtService = jwtService;
        this.gameSaveRepository = gameSaveRepository;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.gameSaveService = new GameSaveService(gameSaveRepository, objectMapper);
    }

    @GetMapping
    public ResponseEntity<List<SavedGameInfo>> getSavedGames(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        // Fetch games where this user is either the creator or a participant
        List<GameSave> saves = gameSaveRepository.findByUsernameInPlayers(username);

        List<SavedGameInfo> gameInfos = saves.stream()
            .map(save -> new SavedGameInfo(
                save.getId(),
                save.getLastSaved(),
                gameSaveService.getPlayerUsernames(save.getPlayerUsernamesCSV()),
                save.getCreatorUsername()
            ))
            .toList();

        return ResponseEntity.ok(gameInfos);
    }

    @PostMapping("/ready")
    public ResponseEntity<GameLoadStatus> markReady(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody ReadyRequest request
    ) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        try {
            System.out.println(request.getGameId());
            readyPlayers.computeIfAbsent(request.getGameId(), k -> new HashSet<>());
            readyPlayers.get(request.getGameId()).add(username);

            GameSave save = gameSaveRepository.findById(request.getGameId())
                .orElseThrow(() -> new RuntimeException("Game not found"));

            List<String> requiredPlayers = new ArrayList<>();
            for (String playerUsername : gameSaveService.getPlayerUsernames(save.getPlayerUsernamesCSV())) {
                if (playerUsername.startsWith("guest") || playerUsername.equals("NPC"))
                    continue;
                requiredPlayers.add(playerUsername);
            }

            boolean allReady = readyPlayers.get(request.getGameId()).containsAll(requiredPlayers);

            return ResponseEntity.ok(new GameLoadStatus(allReady));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/load")
    public ResponseEntity<Void> loadGame(@RequestBody ReadyRequest request,
                                            @RequestHeader("Authorization")String authHeader) {
        System.out.println("Loading " + request.getGameId());
        if (AppServer.getCurrentGame() != null && AppServer.getCurrentGame().getId().equals(request.getGameId())) {
            System.out.println("Appserver game not null" + AppServer.getCurrentGame().getId());
            return ResponseEntity.ok().build();
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        GameSave save = gameSaveRepository.findById(request.getGameId())
            .orElseThrow(() -> new RuntimeException("Game not found"));
        System.out.println("Loading: game found");

        ObjectMapper objectMapper = new ObjectMapper();
        ConnectionMonitor.setShouldQuitGame(false);
        try {
            FullGameDTO fullGameDTO = objectMapper.readValue(save.getSerializedState(), FullGameDTO.class);
            AppServer.setCurrentGame(new Game(fullGameDTO));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }
}
