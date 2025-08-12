package io.github.StardewValley.server.repository;

import io.github.StardewValley.server.model.GameSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GameSaveRepository extends JpaRepository<GameSave, UUID> {
    List<GameSave> findByCreatorUsername(String username);
    @Query("SELECT g FROM GameSave g WHERE g.creatorUsername = :username OR g.playerUsernamesCSV LIKE %:username%")
    List<GameSave> findByUsernameInPlayers(@Param("username") String username);
}

