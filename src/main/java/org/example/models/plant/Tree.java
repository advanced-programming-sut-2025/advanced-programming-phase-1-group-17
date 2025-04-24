package org.example.models.plant;

import org.example.models.Placeable;

public class Tree implements Placeable {
    private TreeType type;
    private boolean isHitByLightning;
    public int age;

    public Tree(TreeType treeType){
        this.type = treeType;
    }

    public void goToNextDay(){

    }

    public TreeType getType() {
        return type;
    }

    public void setType(TreeType type) {
        this.type = type;
    }

    public boolean isHitByLightning() {
        return isHitByLightning;
    }

    public void setHitByLightning(boolean hitByLightning) {
        isHitByLightning = hitByLightning;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
