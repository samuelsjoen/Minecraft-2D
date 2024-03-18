package com.minecraft.game.controller;

import com.badlogic.gdx.Gdx;
import com.minecraft.game.model.Inventory;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.view.CraftingView;

public class OverlayController {
    private Inventory inventory;
    private CraftingView craftingView;

    public OverlayController(Inventory inventory, CraftingView craftingView) {
        this.inventory = inventory;
        this.craftingView = craftingView;
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
        if (Gdx.input.isKeyJustPressed(Constants.CRAFTING_OPEN)) {
            craftingView.open();
        }
    }

}
