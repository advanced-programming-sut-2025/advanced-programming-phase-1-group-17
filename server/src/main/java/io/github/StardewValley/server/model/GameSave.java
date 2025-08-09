package io.github.StardewValley.server.model;

import io.github.StardewValley.shared.TimeAndDateDTO;
import io.github.StardewValley.shared.dto.LightningStateDTO;
import io.github.StardewValley.shared.models.savedClasses.TileSave;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class GameSave {
    @Id
    private UUID id;

    private String creatorUsername;
    private LocalDateTime lastSaved;

    private List<TileSave> tiles;

    @Lob
    private String serializedState; // JSON or binary

    @Embedded
    private TimeAndDateDTO timeAndDate;
    @Embedded
    private LightningStateDTO lightningStateDTO;

    // getters/setters
}

