package org.example.models;

import org.example.models.Product;

public class Trade {
    private Product product;
    private int amount;
    private double price;
    private int id;
    private static int nextTradeId = 1;

    public Trade(Product product, int amount, double price) {
        this.product = product;
        this.amount = amount;
        this.price = price;
        id = nextTradeId++;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
