package io.github.StardewValley.shared.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Result(boolean successful, String message) {
    @JsonCreator
    public Result(@JsonProperty("successful") boolean successful, @JsonProperty("message") String message) {
        this.successful = successful;
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
