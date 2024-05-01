package com.minecraft.game.model.crafting;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.minecraft.game.model.items.Item;
import com.minecraft.game.model.items.ItemMaterial;
import com.minecraft.game.model.items.ItemType;

public class ItemTest {
    @Test
    void testItemInitialization() {
        Item item = Item.DIAMOND_LEGGINGS;
        assert item.getName().equals("Diamond leggings");
        assert item.getType() == ItemType.LEGGINGS;
        assert item.getMaterial() == ItemMaterial.DIAMOND;
        assert item.getDescription() == "A strong pair of leggings for protection";
        assert item.getMaxAmount() == 1;
        assert Arrays.deepEquals(item.getRecipe(), new Item[][] {
            {Item.DIAMOND_ORE, Item.DIAMOND_ORE, Item.DIAMOND_ORE},
            {Item.DIAMOND_ORE, null, Item.DIAMOND_ORE},
            {Item.DIAMOND_ORE, null, Item.DIAMOND_ORE}
        });
        assert item.getTexture() == "inventoryItems/diamond_leggings.png";
    }

    @Test
    void testItemMap() {
        assert Item.getItemWithName("Diamond leggings") == Item.DIAMOND_LEGGINGS;
        assert Item.getItemWithName("Diamond chestplate") == Item.DIAMOND_CHESTPLATE;
        assert Item.getItemWithName("Iron helmet") == Item.IRON_HELMET;
    }
}
