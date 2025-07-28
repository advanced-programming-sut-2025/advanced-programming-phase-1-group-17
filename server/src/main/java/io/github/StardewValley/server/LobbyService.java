package io.github.StardewValley.server;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.StardewValley.server.model.Lobby;
import io.github.StardewValley.server.model.User;
import io.github.StardewValley.server.repository.LobbyRepository;
import io.github.StardewValley.server.repository.UserRepository;
import io.github.StardewValley.shared.GameAssetManager;
import io.github.StardewValley.shared.models.*;
import io.github.StardewValley.shared.models.NPCS.NPC;
import io.github.StardewValley.shared.models.map.Tile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LobbyService {

    private final LobbyRepository lobbyRepository;
    private final UserRepository userRepository;

    @Autowired
    public LobbyService(LobbyRepository lobbyRepository, UserRepository userRepository) {
        this.lobbyRepository = lobbyRepository;
        this.userRepository = userRepository;
    }

    public Lobby getById(String inviteCode) {
        Optional<Lobby> lobby = lobbyRepository.findByInviteCode(inviteCode);
        return lobby.orElse(null);
    }

    public LobbyDto createLobby(String name, boolean isPrivate, boolean isVisible, String adminUsername) {
        String inviteCode = UUID.randomUUID().toString().substring(0, 6);
        Lobby lobby = new Lobby(name, inviteCode, isPrivate, isVisible, adminUsername);
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
            lobby.getPlayerUsernames().add(username);
            lobbyRepository.save(lobby);
        }
        return Optional.of(toDto(lobby));
    }

    public boolean leaveLobby(Long lobbyId, String username) {
        Optional<Lobby> optionalLobby = lobbyRepository.findById(lobbyId);
        if (optionalLobby.isEmpty()) return false;
        Lobby lobby = optionalLobby.get();
        lobby.getPlayerUsernames().remove(username);
        if (lobby.getPlayerUsernames().isEmpty()) {
            lobbyRepository.delete(lobby);
        } else {
            lobbyRepository.save(lobby);
        }
        return true;
    }

    public boolean startGame(Long lobbyId, String adminUsername) {
        Optional<Lobby> optionalLobby = lobbyRepository.findById(lobbyId);
        if (optionalLobby.isEmpty()) return false;
        Lobby lobby = optionalLobby.get();
        if (!lobby.getAdminUsername().equals(adminUsername)) return false;
        lobby.setStatus(LobbyStatus.STARTED);
        lobbyRepository.save(lobby);
        List<String> playerUsernames = lobby.getPlayerUsernames();
        while (playerUsernames.size() == 4) {
            playerUsernames.add("-");
        }
        User user1 = userRepository.findByUsername(playerUsernames.get(0)).get();
        User user2 =  userRepository.findByUsername(playerUsernames.get(1)).get();
        User user3 =  userRepository.findByUsername(playerUsernames.get(2)).get();
        User user4 =  userRepository.findByUsername(playerUsernames.get(3)).get();
        if (user3 == null) {
            if (userRepository.existsByUsername("guest1")) {
                userRepository.delete(userRepository.findByUsername("guest1").get());
            }
            user3 = new User();
            user3.setUsername("guest1");
        }
        if (user4 == null) {
            if (userRepository.existsByUsername("guest2")) {
                userRepository.delete(userRepository.findByUsername("guest2").get());
            }
            user4 = new User();
            user4.setUsername("guest2");
        }
        userRepository.save(user3);
        userRepository.save(user4);
        Tile.getTiles().clear();
        Tile.getTreeTile().clear();

        NPC.setFatherPlayer(null);
        NPC.setFatherUser(null);
        //TODO
//        Game game = new Game(user1,user2, user3, user4);
//        App.setCurrentGame(game);
//        App.getGames().add(game);

        return true;
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
            lobby.getPlayerUsernames()
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
}
