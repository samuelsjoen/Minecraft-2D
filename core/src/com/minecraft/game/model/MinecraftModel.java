package com.minecraft.game.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.controller.ControllableMinecraftModel;
import com.minecraft.game.model.Player.State;
import com.minecraft.game.model.crafting.Crafting;
import com.minecraft.game.model.crafting.Inventory;
import com.minecraft.game.model.crafting.Item;
import com.minecraft.game.model.entities.EntityFactory;
import com.minecraft.game.model.map.MinecraftMap;
import com.minecraft.game.model.map.TileType;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.view.ViewableMinecraftModel;

public class MinecraftModel implements ViewableMinecraftModel, ControllableMinecraftModel {

    private MinecraftMap map;
    @SuppressWarnings("unused")
    private EntityFactory factory;

    private GameState gameState;
    @SuppressWarnings("unused")
    private Player player;
    private Inventory inventory;

    private int jumpCounter = 0; // Jump counter initialized
    private float velX = 0;
    private Crafting crafting;

    private DayNightCycle dayNightCycle;

    public MinecraftModel(MinecraftMap map, EntityFactory factory) {
        this.map = map;
        this.factory = factory;

        // this.gameState = GameState.GAME_ACTIVE;
        this.gameState = GameState.WELCOME_SCREEN;

        this.player = map.getPlayer();
        this.inventory = new Inventory(Constants.DEFAULT_ITEMS);

        this.crafting = new Crafting(getInventory());

        this.dayNightCycle = new DayNightCycle();

    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void setGameState(GameState state) {
        gameState = state;
    }

    @Override
    public World getWorld() {
        return map.getWorld();
    }

    @Override
    public OrthogonalTiledMapRenderer getMapRenderer() {
        return map.setupMap("assets/map/mapExample3-64.tmx");
    }

    @Override
    public TiledMap getTiledMap() {
        return map.getTiledMap();
    }

    @Override
    public Player getPlayer() {
        return map.getPlayer();
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void changeInventorySlot(int i) {
        if (i == -1) {
            inventory.changeSlot(-1);
        } else if (i == +1) {
            inventory.changeSlot(+1);
        }
    }

    @Override
    public void dropInventoryItem() {
        inventory.dropItem();
    }

    // Crafting

    @Override
    public Crafting getCrafting() {
        return crafting;
    }

    @Override
    public void toggleCrafting() {
        crafting.open();
    }

    @Override
    public void revivePlayer() {
        Player.getHealth().revive();
        getPlayer().setCurrentState(Player.State.IDLE);
    }

    @Override
    public void movePlayer(int direction) {
        // direction = 1 for right, -1 for left
        velX = direction * Constants.PLAYER_MOVE_SPEED;
        getPlayer().getBody().setLinearVelocity(velX, getPlayer().getBody().getLinearVelocity().y);
    }

    @Override
    public void stopPlayer() {
        velX = 0;
        getPlayer().getBody().setLinearVelocity(velX, getPlayer().getBody().getLinearVelocity().y);
    }

    // Check if the player is on the ground to reset the jump counter
    public void checkIfPlayerOnGround() {
        if (getPlayer().getBody().getLinearVelocity().y == 0) {
            jumpCounter = 0; // Reset jump counter if player is on the ground
        }
    }

    @Override
    public void playerJump() {
        checkIfPlayerOnGround();
        int maxJumps = 2; // Maximum number of jumps allowed (including initial jump)

        if (jumpCounter < maxJumps) {

            float force = getPlayer().getBody().getMass() * Constants.PLAYER_JUMP_VELOCITY;
            getPlayer().getBody().setLinearVelocity(getPlayer().getBody().getLinearVelocity().x, 0);
            getPlayer().getBody().applyLinearImpulse(new Vector2(0, force), getPlayer().getBody().getPosition(), true);
            jumpCounter++;

        }
    }

    @Override
    public void playerAttack() {
        getPlayer().toggleIsAttacking();
    }

    @Override
    public State getPlayerState() {
        return getPlayer().getCurrentState();
    }

    @Override
    public float getTileDamage(int tileX, int tileY) {
        Cell cell = map.getCell(tileX, tileY);
        if (cell != null) {
            // Get the tile type based on the tile coordinates
            int tileId = cell.getTile().getId();
            TileType tiletype = TileType.getTileTypeWithId(tileId);
            float damage = tiletype.getDamage();            
            return damage;
        }
        return 0;
    }

    @Override
    public void removeBlock(int tileX, int tileY) {
        Cell cell = map.getCell(tileX, tileY);
        if (cell != null) {

            // Get the tile type based on the tile coordinates
            int tileId = cell.getTile().getId();
            TileType tiletype = TileType.getTileTypeWithId(tileId);

            // Get the item based on the tile type
            Item item = Item.getItemWithName(tiletype.getTextureName());
            if (item != null) {
                // Add the item to the inventory
                getInventory().addItem(item);
                // Remove the block from the mineable layer
                map.removeBlock(tileX, tileY);
            }
        }
    }

    @Override
    public void addBlock(int tileX, int tileY) {

        // needs to check if player is in the way (player is 2x1 tiles)
        int playerX = (int) getPlayer().getX() / Constants.TILE_SIZE;
        int playerY = (int) getPlayer().getY() / Constants.TILE_SIZE;

        System.out.println("PlayerX: " + playerX + " PlayerY: " + playerY);
        System.out.println("Tilex: " + tileX + " Tiley: " + tileY);
        if (tileX == playerX && tileY == (playerY - 1) || tileX == playerX && tileY == playerY) {
            return;
        }

        // check if there is already a block at the coordinates
        Cell cell = map.getCell(tileX, tileY);

        if (cell == null) {
            // Get selected item from inventory
            Inventory inventory = getInventory();
            Item item = inventory.getSelectedItem();
            if (item != null) {
                String itemName = item.getName();

                TileType tileType = TileType.getTileTypeWithName(itemName);
                if (tileType != null) {
                    // Remove the item from the inventory
                    inventory.removeItem(item);
                    // Add the block to the mineable layer
                    map.addBlock(tileX, tileY, tileType);
                }
            }
        }
    }

    @Override
    public void restartGame() {
        map = new MinecraftMap();
        factory = new EntityFactory();
        this.inventory = new Inventory(Constants.DEFAULT_ITEMS);
        gameState = GameState.WELCOME_SCREEN;
    }

    @Override
    public DayNightCycle getDayNightCycle() {
        return this.dayNightCycle;
    }

    @Override
    public boolean isBlockMineable(int tileX, int tileY) {
        Cell cell = map.getCell(tileX, tileY);
        if (cell != null) {
            // Get the tile type based on the tile coordinates
            int tileId = cell.getTile().getId();
            TileType tiletype = TileType.getTileTypeWithId(tileId);
            if (tiletype.getDamage() > 0) {
                return true;
            }
        }
        return false;
    }

}
