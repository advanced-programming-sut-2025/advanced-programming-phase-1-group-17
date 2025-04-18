package models.plant;

import models.Placeable;
import models.enums.SeedType;

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
