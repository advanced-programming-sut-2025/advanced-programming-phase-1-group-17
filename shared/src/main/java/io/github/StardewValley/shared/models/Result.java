package io.github.StardewValley.shared.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    private final boolean isSuccessful;
    private final String message;

    @JsonCreator
    public Result(@JsonProperty("isSuccessful") boolean isSuccessful, @JsonProperty("message") String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
