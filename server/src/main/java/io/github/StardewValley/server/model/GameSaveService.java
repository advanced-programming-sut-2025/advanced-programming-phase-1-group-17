package io.github.StardewValley.server.model;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.StardewValley.server.repository.GameSaveRepository;
import io.github.StardewValley.shared.models.saveClasses.FullGameDTO;
import io.github.StardewValley.shared.models.saveClasses.PlayerSave;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameSaveService {
    private final GameSaveRepository repo;
    private final ObjectMapper mapper;

    public GameSaveService(GameSaveRepository repo, ObjectMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Transactional
    public GameSave saveGame(UUID gameId, FullGameDTO gameDTO, String creatorUsername) throws Exception {
        try {
            String jsonState = mapper.writeValueAsString(gameDTO);

            GameSave gameSave = repo.findById(gameId).orElse(new GameSave());
            gameSave.setId(gameId);
            gameSave.setCreatorUsername(creatorUsername);
            gameSave.setLastSaved(java.time.LocalDateTime.now());

            List<String> usernames = new ArrayList<>();
            for (PlayerSave playerSave : gameDTO.getPlayerSaves()) {
                usernames.add(playerSave.getUser().getUsername());
            }

            String csv = String.join(",", usernames);
            gameSave.setPlayerUsernamesCSV(csv);

            gameSave.setSerializedState(jsonState);

            return repo.save(gameSave);
        } catch (JsonMappingException e) {
            System.err.println("JSON mapping failed at path: " + e.getPathReference());
            System.exit(0);
        }
        return null;
    }


    public Optional<FullGameDTO> loadGame(UUID gameId) throws Exception {
        Optional<GameSave> gameSaveOpt = repo.findById(gameId);
        if (gameSaveOpt.isEmpty()) return Optional.empty();

        GameSave gameSave = gameSaveOpt.get();
        String jsonState = gameSave.getSerializedState();

        try {
            FullGameDTO gameDTO = mapper.readValue(jsonState, FullGameDTO.class);
            return Optional.of(gameDTO);
        } catch (JsonMappingException e) {
            e.getPathReference(); // or e.getPathReference() in newer versions
            throw e;
        }
    }


    public List<GameSave> getSavesByUser(String username) {
        return repo.findByCreatorUsername(username);
    }

    public List<String> getPlayerUsernames(String playerUsernamesCSV) {
        if (playerUsernamesCSV == null || playerUsernamesCSV.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(playerUsernamesCSV.split(","));
    }
}

