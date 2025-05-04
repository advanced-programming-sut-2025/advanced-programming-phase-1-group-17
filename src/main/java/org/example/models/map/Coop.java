package org.example.models.map;

import org.example.models.Animal;
import org.example.models.Placeable;
import org.example.models.enums.AnimalPlaceType;

import java.util.ArrayList;

public class Coop implements Placeable {
    private ArrayList<Animal> animals=new ArrayList<>();
    private ArrayList<Tile> tiles=new ArrayList<>();
    private AnimalPlaceType placeType;

}
