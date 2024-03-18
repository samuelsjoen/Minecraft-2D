package com.minecraft.game.controller;

import com.minecraft.game.model.GameState;
//import com.minecraft.game.model.Inventory;
//import com.minecraft.game.model.Player;

public interface ControllableMinecraftModel {

    // GameState

    GameState getGameState();

    void setGameState(GameState state);

    /*
    
    // Inventory
    
    Inventory getInventory();

    void changeInventorySlot(int i);

    void dropInventoryItem();

    // Player

    Player getPlayer(); 

    void revivePlayer();

    void movePlayerRight();

    void movePlayerLeft();

    void playerJump();

    void playerAttack();

    // Tiles/blocks

    void removeBlock(int screenX, int screenY);

    void placeBlock(int screenX, int screenY);
   */ 
}
