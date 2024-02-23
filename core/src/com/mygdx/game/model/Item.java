package com.mygdx.game.model;

public enum Item {
    SWORD("Sword", "A sharp sword for fighting", "sword.png"),
    PICKAXE("Pickaxe", "A pickaxe for mining", "pickaxe.png"),
    DIRT("Dirt", "A block of dirt", "dirt.png"),
    GRASS("Grass", "A block of grass", "grass.png"),
    STONE("Stone", "A block of stone", "stone.png");

    private final String name;
    private final String description;
    private final String texture;

    Item(String name, String description, String texture) {
        this.name = name;
        this.description = description;
        this.texture = texture;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTexture() {
        return texture;
    }
}
