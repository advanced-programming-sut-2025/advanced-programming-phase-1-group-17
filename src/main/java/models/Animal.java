package models;

import models.enums.AnimalType;

import java.util.ArrayList;
import java.util.HashMap;

public class Animal {
    private String name;
    private AnimalType type;
    private int price;
    private HashMap<String, Integer> products = new HashMap<>();
    private int friendship;
    private ArrayList<AnimalProduct> animalProduct=new ArrayList<>();

}
