package org.example.models.tools;

import org.example.models.Product;
import org.example.models.enums.BackPackType;
import org.example.models.plant.Seed;

import java.util.HashMap;

public class BackPack {
    //Attention: It seems there is only one backpack of each type in the whole game

    private HashMap<Seed, Integer> seeds = new HashMap<>();
    private HashMap<Tool, Integer> tools = new HashMap<>();
    private HashMap<Product, Integer> products = new HashMap<>();

    private final BackPackType type;

    public BackPack(BackPackType type) {
        this.type = type;
    }

    private boolean isBackPackFull() {
        int itemCount = seeds.size() + tools.size() + products.size();
        return itemCount >= type.getCapacity();
    }

    public HashMap<Seed, Integer> getSeeds() {
        return seeds;
    }

    public void setSeeds(HashMap<Seed, Integer> seeds) {
        this.seeds = seeds;
    }

    public HashMap<Tool, Integer> getTools() {
        return tools;
    }

    public void setTools(HashMap<Tool, Integer> tools) {
        this.tools = tools;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }

    public BackPackType getType() {
        return type;
    }
}
