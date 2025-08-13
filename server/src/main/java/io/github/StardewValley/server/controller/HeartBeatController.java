package io.github.StardewValley.server.controller;

import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.server.JwtService;
import io.github.StardewValley.server.model.ConnectionService;
import io.github.StardewValley.shared.models.Player;
import io.github.StardewValley.shared.models.Result;
import io.github.StardewValley.shared.models.game.Game;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class HeartBeatController {

    private final ConnectionService connectionService;
    private final JwtService jwtService;

    public HeartBeatController(ConnectionService connectionService, JwtService jwtService) {
        this.connectionService = connectionService;
        this.jwtService = jwtService;
    }

    @PostMapping("/heartbeat")
    public ResponseEntity<Void> heartbeat(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = getUsernameFromToken(authHeader);
        connectionService.updateHeartbeat(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/resume")
    public ResponseEntity<Result> resumeGame(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = getUsernameFromToken(authHeader);
        Game game = AppServer.getCurrentGame();
        if (game == null)
            return ResponseEntity.ok(new Result(false, "There is no ongoing game that you are part of it."));

        boolean found = false;
        for (Player player : game.getPlayers()) {
            if (player.getUser().getUsername().equals(username)) {
                found = true;
                break;
            }
        }
        if (!found)
            return ResponseEntity.ok(new Result(false, "There is no ongoing game that you are part of it."));

        // Mark them as reconnected
        connectionService.updateHeartbeat(username);

        System.out.println(username + " has resumed the game.");
        connectionService.removeDisconnectionTime(username);
        game.setDCPaused(false);
        return ResponseEntity.ok(new Result(true, ""));
    }


    private String getUsernameFromToken(String authHeader) {
        String token = authHeader.substring(7);
        return jwtService.extractUsername(token);
    }
}

