package io.github.StardewValley.server.controller;

import io.github.StardewValley.server.JwtService;
import io.github.StardewValley.server.LobbyService;
import io.github.StardewValley.server.model.Lobby;
import io.github.StardewValley.server.repository.LobbyRepository;
import io.github.StardewValley.shared.models.GameDTO;
import io.github.StardewValley.shared.models.LobbyDto;
import io.github.StardewValley.shared.models.TileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lobbies")
public class LobbyController {

    private final LobbyService lobbyService;
    private final JwtService jwtService;

    @Autowired
    public LobbyController(LobbyService lobbyService, JwtService jwtService) {
        this.lobbyService = lobbyService;
        this.jwtService = jwtService;
    }

    private String getUsernameFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
    }

    @PostMapping("/create")
    public ResponseEntity<LobbyDto> createLobby(@RequestHeader("Authorization") String authHeader, @RequestBody LobbyDto req) {
        String username = getUsernameFromToken(authHeader);
        return ResponseEntity.ok(lobbyService.createLobby(req.getName(), req.isPrivate(), req.isVisible(), username,req.getPassword()));
    }

    @GetMapping("/list")
    public ResponseEntity<List<LobbyDto>> listLobbies() {
        return ResponseEntity.ok(lobbyService.listLobbies());
    }

    @PostMapping("/join")
    public ResponseEntity<LobbyDto> joinLobby(@RequestHeader("Authorization") String authHeader, @RequestParam String inviteCode) {
        String username = getUsernameFromToken(authHeader);
        Optional<LobbyDto> lobbyOpt = lobbyService.joinLobby(inviteCode, username);
        return lobbyOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/leave")
    public ResponseEntity<Void> leaveLobby(@RequestHeader("Authorization") String authHeader, @RequestParam Long lobbyId) {
        String username = getUsernameFromToken(authHeader);
        if (lobbyService.leaveLobby(lobbyId, username)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/changeAdmin")
    public ResponseEntity<Void> changeAdminUserName(@RequestHeader("Authorization") String authHeader, @RequestParam Long lobbyId) {
        if (lobbyService.changeAdminUserName(lobbyId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/start")
    public ResponseEntity<GameDTO> startGame(
        @RequestHeader("Authorization") String authHeader,
        @RequestParam Long lobbyId
    ) {
        String username = getUsernameFromToken(authHeader);
        GameDTO game = lobbyService.startGame(lobbyId, username);
        if (game != null) {
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.status(403).build();
        }
    }
    @DeleteMapping("/deleteCode/{inviteCode}")
    public ResponseEntity<Void> deleteByInviteCode(@PathVariable String inviteCode) {
        lobbyService.deleteLobbyByCode(inviteCode);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        lobbyService.deleteLobbyById(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllLobbies() {
        lobbyService.deleteAllRepo();
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/code/{Id}")
    public LobbyDto getById(@PathVariable Long Id) {
        Lobby lobby = lobbyService.getById(Id);
        return lobbyService.toDto(lobby);
    }

}
