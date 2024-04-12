package com.minecraft.game.model.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class TileTypeTest {

    @Test
    public void testGetTileTypeWithId() {
        TileType grass = TileType.getTileTypeWithId(1);
        assertNotNull(grass);
        assertEquals(TileType.GRASS, grass);
    }

    @Test
    public void testGetTileTypeWithName() {
        TileType dirt = TileType.getTileTypeWithName("Dirt");
        assertNotNull(dirt);
        assertEquals(TileType.DIRT, dirt);
    }

    @Test
    public void testGetDamage() {
        TileType dirt = TileType.getTileTypeWithName("Dirt");
        assertNotNull(dirt);
        assertEquals(1, dirt.getBaseDamage());
    }

}
