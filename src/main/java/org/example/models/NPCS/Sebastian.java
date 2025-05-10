package org.example.models.NPCS;

import org.example.models.Placeable;

import java.util.ArrayList;
import java.util.HashMap;

public class Sebastian extends NPC implements Placeable {
    private int x;
    private int y;
    private String name = "Sebastian";
    private String job = "cook";
    private final HashMap<String, String> dialogue = new HashMap<>();
    {
        dialogue.put("what's your name?", "Sebastian");
        dialogue.put("hello", "hi how are you?");
        dialogue.put("i'm fine how are you", "i'm fine");
        dialogue.put("where is this?", "this is a village in iran.");
        dialogue.put("goodbye", "bye , take care of yourself.");
        dialogue.put("how is the weather?", "excellent!");
        dialogue.put("what is your job?", "I am a cook");
    }
    private ArrayList<String> favorites =new ArrayList<>();

    @Override
    public ArrayList<String> getFavorites() {
        return favorites;
    }
    {
        favorites.add("SheepWool");
        favorites.add("pumpkinPie");
        favorites.add("pizza");
    }
    private ArrayList<Quest> requests= new ArrayList<>();
    public ArrayList<Quest> getRequests(){
        return requests;
    }
    {
        requests.add(new Quest("Delivery of 50 units of iron",0,false,"IRON",50));
        requests.add(new Quest("Delivery of pumpkin pie",1,false,"pumpkinPie",1));
        requests.add(new Quest("Delivery of 150 units of stone",2,false,"Stone",150));
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

    public HashMap<String, String> getDialogue() {
        return dialogue;
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

}
