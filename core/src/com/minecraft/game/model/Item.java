package com.minecraft.game.model;

public enum Item {
    SWORD("Sword", "A sharp sword for fighting", "assets/inventoryItems/sword.png", 1),
    PICKAXE("Pickaxe", "A pickaxe for mining", "assets/inventoryItems/pickaxe.png", 1),
    DIRT("Dirt", "A block of dirt", "dirt.png", 64),
    GRASS("Grass", "A block of grass", "grass.png", 64),
    STONE("Stone", "A block of stone", "stone.png", 64);

    private final String name;
    private final String description;
    private final String texture;
    private final int maxAmount;

    Item(String name, String description, String texture, int maxAmount) {
        this.name = name;
        this.description = description;
        this.texture = texture;
        this.maxAmount = maxAmount;
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

    public int getMaxAmount() {
        return maxAmount;
    }
}
