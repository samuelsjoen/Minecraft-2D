package com.minecraft.game.model.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.minecraft.game.model.items.Item;

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
    public void testGetBaseDamage() {
        TileType dirt = TileType.getTileTypeWithName("Dirt");
        assertNotNull(dirt);
        assertEquals(1, dirt.getBaseDamage());
    }

    @Test
    public void testGetDamage() {
        TileType dirt = TileType.getTileTypeWithName("Dirt");
        // Dirt has a base damage of 1
        float dirtBaseDamage = dirt.getBaseDamage();
        // Diamond pickaxe has a damagemultiplier of 4
        float diamondDamage = 4.0f;
        Item diamondPickaxe = Item.DIAMOND_PICKAXE;
        // Get the damage of the diamond pickaxe on dirt
        float damage = dirt.getDamage(diamondPickaxe);

        assertEquals(dirtBaseDamage/diamondDamage, damage);
    }

    @Test 
    public void testIsCollidable() {
        TileType dirt = TileType.getTileTypeWithName("Dirt");
        assertNotNull(dirt);
        assertEquals(true, dirt.isCollidable());

        TileType leaves = TileType.getTileTypeWithName("Leaves");
        assertNotNull(leaves);
        assertEquals(false, leaves.isCollidable());
    }

    @Test
    public void testGetTextureName() {
        TileType dirt = TileType.getTileTypeWithName("Dirt");
        assertNotNull(dirt);
        assertEquals("Dirt", dirt.getTextureName());
    }

    @Test
    public void testGetId() {
        TileType dirt = TileType.getTileTypeWithName("Dirt");
        assertNotNull(dirt);
        assertEquals(2, dirt.getId());
    }

}
