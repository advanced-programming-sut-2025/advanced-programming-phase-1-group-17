package org.example.models;

import org.example.models.enums.AnimalPlace;

import java.util.ArrayList;
import java.util.HashMap;

public class Animal implements Placeable {
    private String name;
    private AnimalPlace type;
    private int price;
    private HashMap<String, Integer> products = new HashMap<>();
    private int friendship;
    private ArrayList<AnimalProduct> animalProduct=new ArrayList<>();

}
