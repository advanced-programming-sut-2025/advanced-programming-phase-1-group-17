package io.github.StardewValley.server;

import io.github.StardewValley.server.model.Lobby;
import io.github.StardewValley.server.model.LobbyStatus;
import io.github.StardewValley.server.repository.LobbyRepository;
import io.github.StardewValley.shared.models.LobbyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class LobbyService {

    private final LobbyRepository lobbyRepository;

    @Autowired
    public LobbyService(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
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
        return true;
    }

    private LobbyDto toDto(Lobby lobby) {
        return new LobbyDto(
            lobby.getId(),
            lobby.getName(),
            lobby.getInviteCode(),
            lobby.isPrivate(),
            lobby.isVisible(),
            lobby.getAdminUsername(),
            lobby.getPlayerUsernames()
        );
    }
}
