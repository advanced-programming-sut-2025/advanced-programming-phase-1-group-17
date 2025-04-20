package org.example.models;

public class Ability {
    private int farmingAbility = 0;
    private int miningAbility = 0;
    private int foragingAbility = 0;
    private int fishingAbility = 0;
    private int farmingLevel = 0;
    private int miningLevel = 0;
    private int foragingLevel = 0;
    private int fishingLevel = 0;

    public void increaseFarmingAbility(int abilityAdded) {
        //TODO: every time you pick an animal or plant product, you gain 5 ability
        //TODO: in higher levels, you can gain better products

        farmingAbility += abilityAdded;

        if (farmingAbility < 150) return;
        else if (farmingAbility < 250) {
            farmingLevel = 1;
            return;
        }
        else if (farmingAbility < 350) {
            farmingLevel = 2;
            return;
        }
        else if (farmingAbility < 450) {
            farmingLevel = 3;
            return;
        }
        farmingLevel = 4;
    }


    public void increaseMiningAbility(int abilityAdded) {
        //TODO: with every stone broken, you gain 10 points
        //TODO: in lvl2, with every stone, you get another element

        miningAbility += abilityAdded;

        if (miningAbility < 150) return;
        else if (miningAbility < 250){
            miningLevel = 1;
            return;
        }
        else if (miningAbility < 350) {
            miningLevel = 2;
            return;
        }
        else if (miningAbility < 450){
            miningLevel = 3;
            return;
        }
        miningLevel = 4;
    }

    public void increaseForagingAbility(int abilityAdded) {
        //TODO: with every foraging taken, you gain 10 points

        foragingAbility += abilityAdded;

        if (foragingAbility < 150) return;
        else if (foragingAbility < 250){
            foragingLevel = 1;
            return;
        }
        else if (foragingAbility < 350) {
            foragingLevel = 2;
            return;
        }
        else if (foragingAbility < 450){
            foragingLevel = 3;
            return;
        }
        foragingLevel = 4;
    }

    public void increaseFishingAbility(int abilityAdded) {
        //TODO: with every fish fished, you gain 5 points

        fishingAbility += abilityAdded;

        if (fishingAbility < 150) return;
        else if (fishingAbility < 250){
            fishingLevel = 1;
            return;
        }
        else if (fishingAbility < 350) {
            fishingLevel = 2;
            return;
        }
        else if (fishingAbility < 450){
            fishingLevel = 3;
            return;
        }
        fishingLevel = 4;
    }


    public int getFarmingLevel() {
        return farmingLevel;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public int getForagingLevel() {
        return foragingLevel;
    }

    public int getFishingLevel() {
        return fishingLevel;
    }
}
