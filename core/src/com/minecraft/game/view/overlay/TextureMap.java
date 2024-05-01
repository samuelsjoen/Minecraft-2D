package com.minecraft.game.view.overlay;

import com.badlogic.gdx.graphics.Texture;
import com.minecraft.game.model.items.Item;

import java.util.HashMap;

public class TextureMap {

    private HashMap<Item, Texture> textureMap;

    public TextureMap() {
        this.textureMap = new HashMap<>();
    }

    public Texture getTexture(Item item) {
        Texture texture = textureMap.get(item);
        if (texture == null) {
            texture = new Texture(item.getTexture());
            textureMap.put(item, texture);
        }
        return texture;
    }

    public void dispose() {
        for (Texture texture : textureMap.values()) {
            texture.dispose();
        }
    }
}
