package io.github.StardewValley.shared.models.backpack;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.StardewValley.shared.models.Deserializers.BackPackableTypeDeserializer;
import io.github.StardewValley.shared.models.Deserializers.BackPackableTypeKeyDeserializer;

@JsonDeserialize(
    keyUsing = BackPackableTypeKeyDeserializer.class,
    using = BackPackableTypeDeserializer.class
)
public interface BackPackableType  {
    String getName();
    double getPrice();
    String getInventoryTexturePath();
}
