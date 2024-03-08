package com.minecraft.game.controller;

import com.badlogic.gdx.Gdx;
import com.minecraft.game.model.Inventory;
import com.minecraft.game.utils.Constants;

public class InventoryController {
    private Inventory inventory;

    public InventoryController(Inventory inventory) {
        this.inventory = inventory;
    }
    public void update() {
        if (Gdx.input.isKeyJustPressed(Constants.INVENTORY_LEFT)) {
            inventory.changeSlot(-1);
        }
        if (Gdx.input.isKeyJustPressed(Constants.INVENTORY_RIGHT)) {
            inventory.changeSlot(+1);
        }
        if (Gdx.input.isKeyJustPressed(Constants.INVENTORY_DROP)) {
            inventory.dropItem();
        }
    }

}
