package io.github.StardewValley.server.repository;

import io.github.StardewValley.server.model.Lobby;
import io.github.StardewValley.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LobbyRepository extends JpaRepository<Lobby, Long> {
    Optional<Lobby> findByInviteCode(String code);
    List<Lobby> findByIsVisibleTrue();
    void deleteByInviteCode(String inviteCode);
}


