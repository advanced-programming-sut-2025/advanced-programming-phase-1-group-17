package org.example.models;

import org.example.models.enums.AnimalPlaceType;
import org.example.models.enums.AnimalType;

import java.util.ArrayList;
import java.util.HashMap;

public class Animal implements Placeable {
    private String name;
    private AnimalPlaceType animalPlace;
    private AnimalType animalType;
    private int price;
    private int friendship;
    private HashMap<String, Integer> products = new HashMap<>();
    private ArrayList<AnimalProduct> animalProduct=new ArrayList<>();
    private boolean isPettedToday=false;
    private boolean isFedToday=false;

}
