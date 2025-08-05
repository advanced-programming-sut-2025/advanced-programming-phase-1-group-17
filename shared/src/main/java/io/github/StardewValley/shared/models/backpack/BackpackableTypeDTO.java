package io.github.StardewValley.shared.models.backpack;

public class BackpackableTypeDTO {
    private String name;
    private String className;
    private double price;
    private String inventoryTexturePath;

    public BackpackableTypeDTO() {
    }

    public BackpackableTypeDTO(String name, String className, double price, String inventoryTexturePath) {
        this.name = name;
        this.className = className;
        this.price = price;
        this.inventoryTexturePath = inventoryTexturePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInventoryTexturePath() {
        return inventoryTexturePath;
    }

    public void setInventoryTexturePath(String inventoryTexturePath) {
        this.inventoryTexturePath = inventoryTexturePath;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
