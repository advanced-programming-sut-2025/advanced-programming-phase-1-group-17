package io.github.StardewValley.server.repository;

import io.github.StardewValley.server.model.GameSave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameSaveRepository extends JpaRepository<GameSave, UUID> {
    GameSave findByID(UUID uuid);
    List<GameSave> findByCreatorUsername(String username);
}

