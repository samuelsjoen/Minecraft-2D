package com.mygdx.game.model;

public enum Item {
    SWORD("Sword", "A sharp sword for fighting"),
    PICKAXE("Pickaxe", "A pickaxe for mining"),
    DIRT("Dirt", "A block of dirt"),
    GRASS("Grass", "A block of grass"),
    STONE("Stone", "A block of stone");

    private final String name;
    private final String description;

    Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
