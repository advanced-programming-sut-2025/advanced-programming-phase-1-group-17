package org.example.models;

public class Product implements BackPackable{
    private String name;
    private double price;

    public String getType() {
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
}
