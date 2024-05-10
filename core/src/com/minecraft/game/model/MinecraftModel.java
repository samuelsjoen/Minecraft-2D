package com.minecraft.game.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.controller.ControllableMinecraftModel;
import com.minecraft.game.model.Player.State;
import com.minecraft.game.model.map.MinecraftMap;
import com.minecraft.game.model.map.TileType;
import com.minecraft.game.utils.BodyHelperService;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.utils.CursorUtils;
import com.minecraft.game.view.ViewableMinecraftModel;
import com.minecraft.game.model.entities.EntityModel;
import com.minecraft.game.model.items.ArmorInventory;
import com.minecraft.game.model.items.Crafting;
import com.minecraft.game.model.items.Inventory;
import com.minecraft.game.model.items.Item;
import com.minecraft.game.model.items.ItemType;

/**
 * The main model class for the Minecraft game.
 */
public class MinecraftModel implements ViewableMinecraftModel, ControllableMinecraftModel {

    private MinecraftMap map;

    private GameState gameState;
    private Health playerHealth;
    private Player player;
    private Inventory inventory;
    private ArmorInventory armorInventory;

    private int jumpCounter = 0; // Jump counter initialized
    private Crafting crafting;

    private DayNightCycle dayNightCycle;
    private Boolean isLastItemPickaxe;
    private OrthogonalTiledMapRenderer mapRenderer;
    private EntityModel EntityModel;

    private Score score;

    /**
     * Constructs a new instance of the MinecraftModel class.
     * Initializes the game state, map, inventory, player health, armor inventory,
     * player, crafting system, day-night cycle, and entity model.
     */
    public MinecraftModel() {
        this.map = new MinecraftMap();

        this.gameState = GameState.WELCOME_SCREEN;

        this.mapRenderer = map.setupMap("map/minecraftMap-64.tmx");
        this.inventory = new Inventory(Constants.DEMO_ITEMS);
        this.playerHealth = new Health(5, 5);
        this.armorInventory = new ArmorInventory(playerHealth);
        playerHealth.setArmorInventory(armorInventory);
        this.player = initializePlayer();
        this.crafting = new Crafting(inventory, armorInventory);

        this.dayNightCycle = new DayNightCycle();

        // We are never going to start with a pickaxe in inventory - so this can be
        // false
        this.isLastItemPickaxe = false;

        this.EntityModel = new EntityModel();

        this.score = new Score();

    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public EntityModel getEntityModel() {
        return EntityModel;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void setGameState(GameState state) {
        gameState = state;
        handleGameStateChange();
    }

    @Override
    public World getWorld() {
        return map.getWorld();
    }

    @Override
    public OrthogonalTiledMapRenderer getMapRenderer() {
        return this.mapRenderer;
    }

    @Override
    public TiledMap getTiledMap() {
        return map.getTiledMap();
    }

    @Override
    public Player getPlayer() {
        return this.player;
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
        updateCursor();
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
        crafting.toggleOpen();
    }

    @Override
    public State getPlayerState() {
        return getPlayer().getCurrentState();
    }

    @Override
    public DayNightCycle getDayNightCycle() {
        return this.dayNightCycle;
    }

    @Override
    public boolean isBlockMineable(int tileX, int tileY) {
        return map.isTileMineable(tileX, tileY);
    }

    @Override
    public void moveCraftableTableSelection(int row, int col) {
        crafting.moveCraftableTableSelection(row, col);
    }

    @Override
    public void craftItem() {
        crafting.craft();
    }

    @Override
    public void updateMovement(boolean moveLeft, boolean moveRight, boolean isAttacking) {
        getPlayer().updateMovement(moveLeft, moveRight, isAttacking);
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
    public float getTileDamage(int tileX, int tileY) {
        float damage = 0;
        Cell cell = map.getCell(tileX, tileY);
        if (cell != null) {
            // Get the tile type based on the tile coordinates
            int tileId = cell.getTile().getId();
            Item itemSelected = inventory.getSelectedItem();
            TileType tiletype = TileType.getTileTypeWithId(tileId);
            if (itemSelected != null) {
                ItemType itemType = itemSelected.getType();
                if (itemType == ItemType.PICKAXE) {
                    damage = tiletype.getDamage(itemSelected);
                } else {
                    damage = tiletype.getBaseDamage();
                }
            } else {
                damage = tiletype.getBaseDamage();
            }
        }
        return damage;
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
                    if (!inventory.contains(item)) {
                        updateCursor();
                    }
                }
            }
        }
    }

    @Override
    public void restartGame() {
        this.inventory = new Inventory(Constants.DEMO_ITEMS);
        map = new MinecraftMap();
        this.mapRenderer = map.setupMap("map/minecraftMap-64.tmx");
        this.playerHealth = new Health(5, 5);
        this.armorInventory = new ArmorInventory(playerHealth);
        playerHealth.setArmorInventory(armorInventory);
        this.player = initializePlayer();
        this.crafting = new Crafting(inventory, armorInventory);
        score.resetScore();

        dayNightCycle = new DayNightCycle();
        
        gameState = GameState.WELCOME_SCREEN;
    }

    @Override
    public ArmorInventory getArmorInventory() {
        return armorInventory;
    }

    @Override
    public void killAllEntities() {
        EnemyManager.killAllEntities();
    }

    /**
     * Check if the player is on the ground to reset the jump counter.
     */
    public void checkIfPlayerOnGround() {
        if (getPlayer().getBody().getLinearVelocity().y == 0) {
            jumpCounter = 0; // Reset jump counter if player is on the ground
        }
    }

    /**
     * Check and update the game state based on the player's state and the number of
     * nights survived.
     */
    public void checkAndUpdateGameState() {
        if (getPlayerState() == State.DEAD) {
            setGameState(GameState.GAME_OVER);
        } else {
            int nightsSurvived = dayNightCycle.getNumberOfNights();
            if (nightsSurvived >= 3) {
                setGameState(GameState.GAME_WON);
            }
        }
    }

    /**
     * Update the cursor based on the selected pickaxe.
     */
    private void updateCursor() {
        String selectedPickaxe = getSelectedPickaxe();
        if (selectedPickaxe == null) {
            return;
        }
        CursorUtils.setCursorPixmap(selectedPickaxe);
    }

    /**
     * Get the selected pickaxe from the inventory.
     * 
     * @return the selected pickaxe-texture filepath
     */
    private String getSelectedPickaxe() {

        Item selectedItem = inventory.getSelectedItem();

        if (selectedItem == null || selectedItem.getType() != ItemType.PICKAXE) {
            if (isLastItemPickaxe == false) {
                return null;
            }
            isLastItemPickaxe = false;
            return "default_cursor.png";
        } else {
            isLastItemPickaxe = true;
            return selectedItem.getTexture();
        }
    }

    /**
     * Get the player's rectangle.
     * 
     * @return the player's rectangle
     */
    public Rectangle getPlayerRectangle() {
        return map.getPlayerRectangle();
    }

    /**
     * Initialize the player.
     * 
     * @return the player
     */
    private Player initializePlayer() {

        Rectangle rectangle = getPlayerRectangle();

        Body body = BodyHelperService.createBody(
                rectangle.getX() + rectangle.getWidth() / 2,
                rectangle.getY() + rectangle.getHeight() / 2,
                rectangle.getWidth(),
                rectangle.getHeight(),
                null,
                false,
                getWorld(),
                Constants.CATEGORY_PLAYER,
                Constants.MASK_PLAYER,
                "player",
                false);
        return new Player(rectangle.getHeight(), rectangle.getWidth(), body, inventory, playerHealth);
    }

    /**
     * Handle the game state change.
     */
    private void handleGameStateChange() {
        if (gameState == GameState.GAME_ACTIVE) {
            dayNightCycle.startCycle(60f); // Start the day-night cycle with a # sec interval 1 minute
        } else if (gameState == GameState.GAME_PAUSED) {
            dayNightCycle.pauseCycle();
        }
    }
}
