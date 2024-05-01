package com.minecraft.game.view.overlay;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.Texture;
import com.minecraft.game.LibgdxUnitTest;
import com.minecraft.game.model.items.Item;

public class TextureMapTest extends LibgdxUnitTest {
    private TextureMap textureMap;

    @BeforeEach
    public void setUp() {
        this.textureMap = new TextureMap();
    }

    @Test
    public void testGetInitializedTexture() {
        Texture texture = textureMap.getTexture(Item.BEDROCK);
        Texture expectedTexture = new Texture(Item.BEDROCK.getTexture());
        Assertions.assertEquals(expectedTexture.toString(), texture.toString());
}}
