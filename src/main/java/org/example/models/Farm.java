package org.example.models;

import java.util.ArrayList;

public class Farm {
    private GreenHouse greenHouse;
    private ArrayList<Lake> lakes = new ArrayList<>();
    private Quarry quarrie = new Quarry();
    private Hut hut = new Hut();
    public void randomFill(){}
    private ArrayList<Coop> coops=new ArrayList<>();
    private ArrayList<Barn> barns=new ArrayList<>();
    private ArrayList<Animal> animals=new ArrayList<>();

    public GreenHouse getGreenHouse() {
        return greenHouse;
    }

    public void setGreenHouse(GreenHouse greenHouse) {
        this.greenHouse = greenHouse;
    }

    public ArrayList<Lake> getLakes() {
        return lakes;
    }

    public void setLakes(ArrayList<Lake> lakes) {
        this.lakes = lakes;
    }

    public Quarry getQuarries() {
        return quarrie;
    }

    public void setQuarries(Quarry quarrie) {
        this.quarrie = quarrie;
    }

    public Hut getHut() {
        return hut;
    }

    public void setHut(Hut hut) {
        this.hut = hut;
    }
}
