package org.example.models.plant;

import org.example.models.Placeable;

public class Seed implements Placeable {
    private boolean isMixed;
    private SeedType type;

    public boolean isMixed() {
        return isMixed;
    }

    public void setMixed(boolean mixed) {
        isMixed = mixed;
    }

    public SeedType getType() {
        return type;
    }

    public void setType(SeedType type) {
        this.type = type;
    }
}
