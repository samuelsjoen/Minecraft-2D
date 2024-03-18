package com.mygdx.game.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.minecraft.game.utils.TileType;

public class TileTypeTest {

    @Test
    public void testGetTileTypeWithId() {
        TileType grass = TileType.getTileTypeWithId(1);
        assertNotNull(grass);
        assertEquals(TileType.GRASS, grass);
    }

    @Test
    public void testGetTileTypeWithName() {
        TileType dirt = TileType.getTileTypeWithName("dirt");
        assertNotNull(dirt);
        assertEquals(TileType.DIRT, dirt);
    }

}
