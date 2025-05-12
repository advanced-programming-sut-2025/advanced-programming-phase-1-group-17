package org.example.models.NPCS;

import org.example.models.Player;

public class Quest {
    private int level;
    private int amount;
    private String item;
    private boolean completed;
    private String questExplanation;
    private boolean isActive = true;
    public Quest(String questExplanation, int level, boolean completed, String item, int amount) {
        this.questExplanation = questExplanation;
        this.level = level;
        this.completed = completed;
        this.item = item;
        this.amount = amount;
        if (level == 2) {
            isActive = false;
        }
    }



    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getQuestExplanation() {
        return questExplanation;
    }

    public void setQuestExplanation(String questExplanation) {
        this.questExplanation = questExplanation;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
