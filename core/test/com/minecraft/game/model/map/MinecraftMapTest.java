package com.minecraft.game.model.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.minecraft.game.LibgdxUnitTest;
import com.minecraft.game.model.MinecraftModel;
import com.minecraft.game.utils.Constants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class MinecraftMapTest extends LibgdxUnitTest {

	private MinecraftMapHelper minecraftMapHelper;
	private TiledMap tiledMap;
	private TiledMapTileLayer tiledMapLayer;
	private MapLayer objectLayer;
	private String mapPath;
	@SuppressWarnings("unused")
	private MinecraftModel modelMock;

	/**
	 * Method run before everything else
	 */
	@BeforeEach
	void setUp() {
		modelMock = mock(MinecraftModel.class);

		minecraftMapHelper = new MinecraftMapHelper();

		mapPath = "map/testMap-64.tmx";
		tiledMap = minecraftMapHelper.setupMapNoRender(mapPath);
		tiledMapLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
		objectLayer = tiledMap.getLayers().get("collisions");

	}

	@Test
	public void testSetupMap() {
		// Should be null before setup
		tiledMap = null;
		assertNull(tiledMap);
		minecraftMapHelper.setupMapNoRender(mapPath);
		assertNotNull(minecraftMapHelper.getTiledMap());

		try {
			minecraftMapHelper.setupMapNoRender("InvalidFilePath");
		} catch (Exception e) {
			// Ignore the exception which will occur in this case because the filepath is
			// invalid.
		}

		assertNull(minecraftMapHelper.getTiledMap());
	}

	@Test
	public void removeBlockTest() {
		// The bedrock tile and object should be present
		assertNotNull(tiledMapLayer.getCell(0, 0));
		assertNotNull(objectLayer.getObjects().get("0, 0"));

		// Remove the bedrock block
		minecraftMapHelper.removeBlock(0, 0);

		// The bedrock tile and object should be removed
		assertNull(tiledMapLayer.getCell(0, 0));
		assertNull(objectLayer.getObjects().get("0, 0"));
	}

	@Test
	public void addBlockTest() {
		int mapHeight = tiledMapLayer.getHeight() - 1;

		// There should be no tile nor object at the given coordinates
		assertNull(tiledMapLayer.getCell(0, mapHeight));
		assertNull(objectLayer.getObjects().get("0, " + mapHeight));

		// Add a block
		minecraftMapHelper.addBlock(0, mapHeight, TileType.GRASS);

		// The object should be present with the given coordinates
		int worldX = 0 * Constants.TILE_SIZE;
		int worldY = mapHeight * Constants.TILE_SIZE;

		// The grass tile and object should be present
		assertNotNull(tiledMapLayer.getCell(0, mapHeight));
		assertNotNull(objectLayer.getObjects().get(worldX + ", " + worldY));
	}
}