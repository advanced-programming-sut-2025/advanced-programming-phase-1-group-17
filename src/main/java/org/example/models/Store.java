package org.example.models;

import org.example.models.enums.StoreType;

import java.util.ArrayList;
public class Store {
    private String storeName;
    private ArrayList<Product> products;
    private String ownerName;
    private int openingHour;
    private int closingHour;
    private StoreType type;
}
