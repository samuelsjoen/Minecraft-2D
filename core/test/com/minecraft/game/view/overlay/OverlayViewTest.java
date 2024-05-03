package com.minecraft.game.view.overlay;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.minecraft.game.LibgdxUnitTest;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Score;
import com.minecraft.game.model.items.Crafting;
import com.minecraft.game.model.items.Inventory;
import com.minecraft.game.model.items.Item;

public class OverlayViewTest extends LibgdxUnitTest {

    private SpriteBatch batch;
    private Crafting crafting;
    private Inventory inventory;
    private Health health;
    private OverlayView overlayView;
    private BitmapFont font;
    private Score score;

    @BeforeEach
    public void setUp() {
        batch = mock(SpriteBatch.class);

        inventory = mock(Inventory.class);
        crafting = mock(Crafting.class);
        when(crafting.isOpen()).thenReturn(true);
        when(crafting.getTable()).thenReturn(new Item[3][3]);
        when(crafting.getCraftableItems()).thenReturn(new Item[5][10]);
    
        health = mock(Health.class);
        when(health.isAlive()).thenReturn(true);
        when(health.getHealth()).thenReturn(5);
        when(health.getArmorHealth()).thenReturn(10);

        score = mock(Score.class);
        when(score.getScore()).thenReturn(0);

        font = mock(BitmapFont.class);
        BitmapFont.BitmapFontData fontData = mock(BitmapFont.BitmapFontData.class);
        when(font.getData()).thenReturn(fontData);
        doNothing().when(fontData).setScale(2);

        overlayView = new OverlayView(inventory, health, crafting, score, batch, font);
    }

    @AfterEach
    public void tearDown() {
        overlayView.dispose();
    }

    @Test
    public void testRender() {
        overlayView.render();
        verify(batch, times(4)).draw(any(Texture.class), anyFloat(), anyFloat());
    }
}
