package models.enums;

public enum Characters {
    Tree("T");
    private String character;
    private Characters(String character) {
        this.character = character;
    }
    public String getCharacter() {
        return character;
    }
}
