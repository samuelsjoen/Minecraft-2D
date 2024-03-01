package com.minecraft.game.model;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.minecraft.game.utils.Constants;

public class Inventory extends GameEntity {
    private HashMap<Item, Integer> items;
    private int maxItemSlots;
    private int currentItems;
    private int currentSlot;
    private Texture inventorySprite;
    private Texture selectedItem;
    private float xItm, yItm;
    private float invJump;
    private float scale;

    public Inventory(float width, float height, Body body) {
        super(width, height, body);
        this.items = new HashMap<Item, Integer>();
        this.maxItemSlots = 9;
        this.currentItems = items.size();
        this.currentSlot = 0;
        this.scale = 1f;
        this.inventorySprite = new Texture(Gdx.files.internal("assets/inventory.png"));
        this.selectedItem = new Texture(Gdx.files.internal("assets/selectedItem.png"));

        // Coordinates for the inventory

        // Coordinates for the first item in inventory
        // The amount of pixels to jump to the next item slot
        this.invJump = 40;

        this.scale = 1f;

        // Default items
        addItem(Item.SWORD);
        addItem(Item.SWORD);
        addItem(Item.SWORD);
        addItem(Item.SWORD);
    }

    private void addItem(Item name, int quantity) {
        if (items.containsKey(name)) {
            items.put(name, items.get(name) + quantity);
        } else {
            if (!isFull()) {
                items.put(name, quantity);
            }
        }
    }

    private void addItem(Item name) {
        addItem(name, 1);
    }

    private void removeItem(Item name, int quantity) {
        if (items.containsKey(name)) {
            items.put(name, items.get(name) - quantity);
            if (items.get(name) <= 0) {
                items.remove(name);
            }
        }
    }

    private void removeItem(Item name) {
        removeItem(name, 1);
    }

    private void removeAllItems(Item name) {
        items.remove(name);
    }

    private boolean isFull() {
        return currentItems >= maxItemSlots;
    }

    private void changeSlot(int slot) {
        int nextSlot = currentSlot + slot;

        if (nextSlot < 0) {
            currentSlot = 9;
        }
        else if (nextSlot > maxItemSlots) {
            currentSlot = 0;
        }
        else {
            currentSlot = nextSlot;
        }
    }

    private void dropItem() {
        if (items.size() > 0) {
            Item item = (Item) items.keySet().toArray()[currentSlot];
            removeItem(item);
        }
    }

    private void renderItems(SpriteBatch batch) {
        int iteration = 0;
        for (Item item : items.keySet()) {
            Texture itemTexture = new Texture(Gdx.files.internal(item.getTexture()));
            batch.draw(itemTexture, xItm + (iteration * invJump), yItm + (iteration * invJump), 23, 23);
            iteration++;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(inventorySprite, x, y);
        renderCurrentSlot(batch);
        renderItems(batch);
    }

    private void renderCurrentSlot(SpriteBatch batch) {
        batch.draw(selectedItem, x + (currentSlot * invJump), y);
    }

    @Override
    public void update() {
        x = body.getPosition().x * Constants.PPM-600;
        y = body.getPosition().y * Constants.PPM+300;
        xItm = x + 5;
        yItm = y + 5;
        checkUserInput();
    }

    private void checkUserInput() {
        if (Gdx.input.isKeyJustPressed(Constants.INVENTORY_LEFT)) {
            changeSlot(-1);
        }
        if (Gdx.input.isKeyJustPressed(Constants.INVENTORY_RIGHT)) {
            changeSlot(+1);
        }
        if (Gdx.input.isKeyJustPressed(Constants.INVENTORY_DROP)) {
            dropItem();
        }
    }

}