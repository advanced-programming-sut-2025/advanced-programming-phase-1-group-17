package io.github.StardewValley.server;


import io.github.StardewValley.server.model.Lobby;
import io.github.StardewValley.server.model.User;
import io.github.StardewValley.server.repository.LobbyRepository;
import io.github.StardewValley.server.repository.UserRepository;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.game.Game;
import io.github.StardewValley.shared.models.game.GameDTO;
import io.github.StardewValley.shared.models.map.Tile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class LobbyService {

    private final LobbyRepository lobbyRepository;
    private final UserRepository userRepository;
    private final Map<Long, ScheduledFuture<?>> lobbyDeleteTasks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler;


    @Autowired
    public LobbyService(LobbyRepository lobbyRepository, UserRepository userRepository, ScheduledExecutorService scheduler) {
        this.lobbyRepository = lobbyRepository;
        this.userRepository = userRepository;
        this.scheduler = scheduler;
    }

    public Lobby getById(Long Id) {
        Optional<Lobby> lobby = lobbyRepository.findById(Id);
        return lobby.orElse(null);
    }

    public LobbyDto createLobby(String name, boolean isPrivate, boolean isVisible, String adminUsername, String password) {
        String inviteCode = UUID.randomUUID().toString().substring(0, 6);
        Lobby lobby = new Lobby(name, inviteCode, isPrivate, isVisible, adminUsername, password);
        lobby.getPlayerUsernames().add(adminUsername);
        lobbyRepository.save(lobby);
        return toDto(lobby);
    }

    public List<LobbyDto> listLobbies() {
        return lobbyRepository.findByIsVisibleTrue().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<LobbyDto> joinLobby(String inviteCode, String username) {
        Optional<Lobby> optionalLobby = lobbyRepository.findByInviteCode(inviteCode);
        if (optionalLobby.isEmpty()) return Optional.empty();
        Lobby lobby = optionalLobby.get();
        if (!lobby.getPlayerUsernames().contains(username)) {
            if (lobby.getPlayerUsernames().isEmpty()) {
                lobby.setAdminUsername(username);
            }
            lobby.getPlayerUsernames().add(username);
            lobbyRepository.save(lobby);
        }
        cancelLobbyDeleteTask(lobby.getId());
        return Optional.of(toDto(lobby));
    }

    public boolean leaveLobby(Long lobbyId, String username) {
        Optional<Lobby> optionalLobby = lobbyRepository.findById(lobbyId);
        if (optionalLobby.isEmpty()) return false;

        Lobby lobby = optionalLobby.get();
        lobby.getPlayerUsernames().remove(username);
        if (lobby.getPlayerUsernames().isEmpty()) {
            lobby.setAdminUsername(null);
        }

        if (lobby.getPlayerUsernames().isEmpty()) {

            if (!lobbyDeleteTasks.containsKey(lobbyId)) {
                ScheduledFuture<?> task = scheduler.schedule(() -> {
                    try {
                        lobbyRepository.deleteById(lobbyId);
                        lobbyRepository.flush();
                        lobbyDeleteTasks.remove(lobbyId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 5, TimeUnit.MINUTES);

                lobbyDeleteTasks.put(lobbyId, task);
            }
            lobbyRepository.save(lobby);
        } else {
            lobbyRepository.save(lobby);
            cancelLobbyDeleteTask(lobbyId);
        }
        return true;
    }

    private void cancelLobbyDeleteTask(Long lobbyId) {
        ScheduledFuture<?> task = lobbyDeleteTasks.remove(lobbyId);
        if (task != null && !task.isCancelled()) {
            task.cancel(false);
            System.out.println("Lobby " + lobbyId + " delete timer canceled.");
        }
    }

    public boolean changeAdminUserName(Long lobbyId) {
        Optional<Lobby> optionalLobby = lobbyRepository.findById(lobbyId);
        if (optionalLobby.isEmpty()) return false;
        Lobby lobby = optionalLobby.get();
        lobby.setAdminUsername(lobby.getPlayerUsernames().get(1));
        if (lobby.getPlayerUsernames().isEmpty()) {
            lobbyRepository.delete(lobby);
        } else {
            lobbyRepository.save(lobby);
        }
        return true;
    }

    public GameDTO startGame(Long lobbyId, String adminUsername) {
        Optional<Lobby> optionalLobby = lobbyRepository.findById(lobbyId);
        if (optionalLobby.isEmpty()) return null;
        Lobby lobby = optionalLobby.get();
        if (!lobby.getAdminUsername().equals(adminUsername)) return null;
        lobby.setStatus(LobbyStatus.STARTED);
        lobbyRepository.save(lobby);
        List<String> playerUsernames = lobby.getPlayerUsernames();
        int i = 1;
        while (playerUsernames.size() < 4) {
            playerUsernames.add("guest" + i);
            i++;
        }
        User user1 = null, user2 = null, user3 = null, user4 = null;
        user1 = userRepository.findByUsername(playerUsernames.get(0)).get();
        user2 = userRepository.findByUsername(playerUsernames.get(1)).get();
        for (int j = 2; j < 4; j++) {
            if (userRepository.existsByUsername(playerUsernames.get(j))) {
                if (j == 2) user3 = userRepository.findByUsername(playerUsernames.get(j)).get();
                else user4 = userRepository.findByUsername(playerUsernames.get(j)).get();
            }
        }

        if (user3 == null) {
            if (userRepository.existsByUsername("guest1")) {
                userRepository.delete(userRepository.findByUsername("guest1").get());
            }
            user3 = new User();
            user3.setUsername("guest1");
            userRepository.save(user3);
        }
        if (user4 == null) {
            if (userRepository.existsByUsername("guest2")) {
                userRepository.delete(userRepository.findByUsername("guest2").get());
            }
            user4 = new User();
            user4.setUsername("guest2");
            userRepository.save(user4);
        }
        Tile.getTiles().clear();
        Tile.getTreeTile().clear();

        NPC.setFatherPlayer(null);
        NPC.setFatherUser(null);

        Game game = new Game(userDTO(user1), userDTO(user2), userDTO(user3), userDTO(user4));
        AppServer.setCurrentGame(game);
        user1.setActiveGame(game);
        user2.setActiveGame(game);
        user3.setActiveGame(game);
        user4.setActiveGame(game);
        return toDto(game);
    }

    public LobbyDto toDto(Lobby lobby) {
        return new LobbyDto(
            lobby.getId(),
            lobby.getName(),
            lobby.getInviteCode(),
            lobby.isPrivate(),
            lobby.isVisible(),
            lobby.getStatus(),
            lobby.getAdminUsername(),
            lobby.getPlayerUsernames(),
            lobby.getPassword()
        );
    }

    public UserDTO userDTO(User user) {
        return new UserDTO(
            user.getUsername(),
            user.getNickName(),
            user.getGender(),
            user.getSecurityQuestion(),
            user.getSecurityAnswer(),
            user.getEmail(),
            user.getPasswordHash()
        );
    }


    public void deleteLobbyByCode(String inviteCode) {
        if (!lobbyRepository.findByInviteCode(inviteCode).isPresent()) {
            throw new RuntimeException("Lobby not found");
        }
        lobbyRepository.deleteByInviteCode(inviteCode);
    }

    public void deleteLobbyById(Long id) {
        if (!lobbyRepository.existsById(id)) {
            throw new RuntimeException("Lobby not found");
        }
        lobbyRepository.deleteById(id);
    }

    public void deleteAllRepo() {
        lobbyRepository.deleteAll();
    }

    public static GameDTO toDto(Game game) {
        List<String> playerUsernames = game.getPlayers().stream()
            .map(p -> p.getUser().getUsername())
            .collect(Collectors.toList());

        return new GameDTO(
            game.getCreator().getUser().getUsername(),
            playerUsernames,
            game.getDate().toString()
        );
    }
}
