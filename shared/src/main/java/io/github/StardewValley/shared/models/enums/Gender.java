package io.github.StardewValley.shared.models.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Gender {
    Male, Female;
    public String toString() {
        return this.name();
    }
}
