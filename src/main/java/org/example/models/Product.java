package org.example.models;

public class Product implements BackPackable{
    private ProductType type;
    private String name;
    private double price;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public ProductType getType() {
        return type;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
