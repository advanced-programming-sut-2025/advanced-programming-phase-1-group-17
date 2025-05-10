package org.example.models.NPCS;

import org.example.models.Placeable;

import java.util.ArrayList;
import java.util.HashMap;

public class Robin extends NPC implements Placeable {
    private int x;
    private int y;
    private String name = "Robin";
    private String job = "architect";
    private final HashMap<String, String> dialogue = new HashMap<>();
    {
        dialogue.put("what's your name?", "Robin");
        dialogue.put("hello", "hi how are you?");
        dialogue.put("i'm fine how are you", "i'm fine");
        dialogue.put("where is this?", "this is a village in iran.");
        dialogue.put("goodbye", "bye , take care of yourself.");
        dialogue.put("how is the weather?", "excellent!");
        dialogue.put("what is your job?", "I am a architect");
    }
    private ArrayList<String> favorites = new ArrayList<>();

    @Override
    public ArrayList<String> getFavorites() {
        return favorites;
    }
    {
        favorites.add("IronBar");
        favorites.add("Wood");
        favorites.add("spaghetti");
    }
    private ArrayList<Quest> requests= new ArrayList<>();
    public ArrayList<Quest> getRequests(){
        return requests;
    }
    {
        requests.add(new Quest("Delivery of 80 sticks",0,false,"Wood",80));
        requests.add(new Quest("Delivery of 10 iron ingots",1,false,"IronBar",10));
        requests.add(new Quest("Delivery of 1000 sticks",2,false,"Wood",1000));
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
