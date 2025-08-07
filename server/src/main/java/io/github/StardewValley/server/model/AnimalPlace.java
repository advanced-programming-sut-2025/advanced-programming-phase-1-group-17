package io.github.StardewValley.server.model;

import io.github.StardewValley.shared.models.animal.AnimalPlaceType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents an animal building on the farm in the server.
 * This is the SINGLE SOURCE OF TRUTH for an animal place.
 */
public class AnimalPlace {

    // --- State Fields ---
    private String id;
    private static int nextId = 1;
    private AnimalPlaceType animalPlaceType;
    private float x, y;
    private int capacity;
    private boolean isDoorOpen = true;

    // This list holds the ACTUAL server-side Animal objects.
    private List<Animal> animals;

    // --- Constructor ---
    public AnimalPlace(AnimalPlaceType animalPlaceType, float x, float y) {
        this.id = String.valueOf(nextId++);
        this.animalPlaceType = animalPlaceType;
        this.capacity = animalPlaceType.getCapacity();
        this.x = x;
        this.y = y;
        this.animals = new ArrayList<>();
    }

    // --- Game Logic Methods ---

    /**
     * Adds an animal to this building if there is capacity.
     * @param animal The server-side Animal object to add.
     * @return true if the animal was added, false otherwise.
     */
    public boolean addAnimal(Animal animal) {
        if (animals.size() >= capacity) {
            return false; // It's full
        }
        return animals.add(animal);
    }

    /**
     * Checks if the building is at full capacity.
     */
    public boolean isFull() {
        return animals.size() >= capacity;
    }

    /**
     * Opens the door and lets all animals know they can go outside.
     */
    public void openDoor() {
        this.isDoorOpen = true;
        for (Animal animal : animals) {
            animal.setOutside(true);
        }
    }

    /**
     * Closes the door.
     */
    public void closeDoor() {
        this.isDoorOpen = false;
        // You might add logic here to command animals to return inside.
    }


    // --- Getters and Setters ---
    public String getId() { return id; }
    public float getX() { return x; }
    public float getY() { return y; }
    public List<Animal> getAnimals() { return animals; }
    public AnimalPlaceType getAnimalPlaceType() { return animalPlaceType; }
}
