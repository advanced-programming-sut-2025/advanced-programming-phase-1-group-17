package org.example.models.NPCS;

import org.example.models.Placeable;

import java.util.ArrayList;
import java.util.HashMap;

public class Harvey extends NPC implements Placeable {
    private int x;
    private int y;
    private String name = "Harvey";
    private String job = "teacher";
    private final HashMap<String, String> dialogue = new HashMap<>();
    {
        dialogue.put("what's your name?", "Harvey");
        dialogue.put("hello", "hi how are you?");
        dialogue.put("i'm fine how are you", "i'm fine");
        dialogue.put("where is this?", "this is a village in iran.");
        dialogue.put("goodbye", "bye , take care of yourself.");
        dialogue.put("how is the weather?", "excellent!");
        dialogue.put("what is your job?", "I am a teacher");
    }
    private ArrayList<String> favorites = new ArrayList<>();

    @Override
    public ArrayList<String> getFavorites() {
        return favorites;
    }
    {
        favorites.add("");
    }
    private ArrayList<Quest> requests= new ArrayList<>();
    public ArrayList<Quest> getRequests(){
        return requests;
    }
    {
        requests.add(new Quest("Delivery of 12 pieces of a desired CarrotSeed",0,false,"CarrotSeeds",12));
        requests.add(new Quest("Delivery of a salmon fish",1,false,"SALMON",1));
        //TODO
        requests.add(new Quest("Delivery of a bottle of wine",2,false," ",1));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public HashMap<String, String> getDialogue() {
        return dialogue;
    }
}
