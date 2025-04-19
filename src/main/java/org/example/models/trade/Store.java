package org.example.models.trade;

import org.example.models.Product;
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
