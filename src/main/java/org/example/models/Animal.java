package org.example.models;

import org.example.models.enums.AnimalHabitat;

import java.util.ArrayList;
import java.util.HashMap;

public class Animal implements Placeable {
    private String name;
    private AnimalHabitat type;
    private int price;
    private HashMap<String, Integer> products = new HashMap<>();
    private int friendship;
    private ArrayList<AnimalProduct> animalProduct=new ArrayList<>();
    //tes
}
