package io.github.StardewValley.server.model;

import io.github.StardewValley.server.AppServer;
import io.github.StardewValley.server.model.ConnectionService;
import io.github.StardewValley.server.model.GameSaveService;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.saveClasses.FullGameDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ConnectionMonitor {

    private final ConnectionService connectionService;
    private final GameSaveService gameSaveService;
    private static boolean shouldQuitGame = false;

    public ConnectionMonitor(ConnectionService connectionService, GameSaveService gameSaveService) {
        this.connectionService = connectionService;
        this.gameSaveService = gameSaveService;
    }

    @Scheduled(fixedRate = 5000)
    public void checkConnections() {
        connectionService.getLastSeenMap().forEach((username, lastSeen) -> {
            if (!connectionService.isConnected(username)) {
                connectionService.markDisconnected(username);

                if (connectionService.shouldSaveAndQuit(username)) {
                    System.out.println(username + " did not return in time. Saving and quitting...");
                    Game game = AppServer.getCurrentGame();
                    try {
                        gameSaveService.saveGame(
                            game.getId(),
                            new FullGameDTO(game),
                            game.getCreator().getUser().getUsername()
                        );
                        shouldQuitGame = true;
                        AppServer.setCurrentGame(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(username + " has disconnected. Waiting for reconnection...");
                    AppServer.getCurrentGame().setDCPaused(true);
                    // TODO: trigger freeze logic and notify other players here
                }
            }
        });
    }

    public static boolean isShouldQuitGame() {
        return shouldQuitGame;
    }
}
