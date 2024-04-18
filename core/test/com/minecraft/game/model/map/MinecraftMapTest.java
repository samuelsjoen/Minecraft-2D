package com.minecraft.game.model.map;

import com.badlogic.gdx.maps.MapLayer;
//import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.World;
//import com.minecraft.game.view.screens.GameScreen;
import com.minecraft.game.LibgdxUnitTest;
//import com.minecraft.game.model.Player;
import com.minecraft.game.utils.Constants;
//import com.minecraft.game.model.map.TileType;

import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.Mockito.*;

public class MinecraftMapTest extends LibgdxUnitTest {

	private MinecraftMapHelper minecraftMapHelper;
	private TiledMap tiledMap;
	//private Player playerMock;
	private TiledMapTileLayer tiledMapLayer;
	private MapLayer objectLayer;
	private String mapPath;

	/**
	 * Method run before everything else
	 */
	@BeforeEach
	void setUp() {
		// mocking
		//gameScreenMock = mock(GameScreen.class);

		minecraftMapHelper = new MinecraftMapHelper();

		//playerMock = mock(Player.class);
		// stubbing
		//when(gameScreenMock.getPlayer()).thenReturn(playerMock);

		// create instance of World
		//world = new World(new Vector2(0, -25f), false);
		// stubbing
		//when(gameScreenMock.getWorld()).thenReturn(world);

		mapPath = "assets/map/mapExample2-64.tmx";
		//tiledMap = tileMapHelper.setupMapNoRender(mapPath);
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
			// Ignore the exception
		}
	
		assertNull(minecraftMapHelper.getTiledMap());
	}

	/*@Test
	public void removeBlockTest() {
		// The bedrock tile and object should be present
		assertNotNull(tiledMapLayer.getCell(0, 0));
		assertNotNull(objectLayer.getObjects().get("0, 0"));

		MapObject polygon = objectLayer.getObjects().get("0, 0");
		int objectId = (int) polygon.getProperties().get("id");
		System.out.println("Object id: " + objectId);

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
	}*/

	@SuppressWarnings("unused")
	private void changeBlock(String change, int x, int y) {
		if (change == "remove") {
			minecraftMapHelper.removeBlock(x, y);
		} else if (change == "add") {
			minecraftMapHelper.addBlock(x, y, TileType.GRASS);
		}	
	}

	@SuppressWarnings("unused")
	private void addingABlock(int x, int y) {
		// The object should be present with the given coordinates
		int worldX = x * Constants.TILE_SIZE;
		int worldY = y * Constants.TILE_SIZE;

		// There should be no tile nor object at the given coordinates
		assertNull(tiledMapLayer.getCell(x, y));
		assertNull(objectLayer.getObjects().get(worldX + ", " + worldY));

		// Add a block
		minecraftMapHelper.addBlock(x, y, TileType.GRASS);

		// The grass tile and object should be present
		assertNotNull(tiledMapLayer.getCell(x, y));
		assertNotNull(objectLayer.getObjects().get(worldX + ", " + worldY));
	}

/*
	@Test
	public void testAddBlockAtPlayer() {
		// The object should be present with the given coordinates
		float worldX = 1 * Constants.TILE_SIZE;
		float worldY = 17 * Constants.TILE_SIZE;

		// stubbing the player's position
		when(playerMock.getX()).thenReturn(worldX);
		when(playerMock.getY()).thenReturn(worldY);

		// top of the player
		assertNull(tiledMapLayer.getCell(1, 16));
		assertNull(objectLayer.getObjects().get(worldX + ", " + (worldY - 64)));

		// bottom of the player
		assertNull(tiledMapLayer.getCell(1, 17));
		assertNull(objectLayer.getObjects().get(worldX + ", " + worldY));

		// Add a block at the player's position
		minecraftMapHelper.addBlock(1, 16, TileType.GRASS);
		minecraftMapHelper.addBlock(1, 17, TileType.GRASS);

        // ! idk if MinecraftMap even has the ability to know where player is anymore.
		// The grass tile and object shouldn't be present
		/*assertNull(tiledMapLayer.getCell(1, 16));
		assertNull(objectLayer.getObjects().get(worldX + ", " + (worldY - 64)));

		// The grass tile and object should be present
		assertNull(tiledMapLayer.getCell(1, 17));
		assertNull(objectLayer.getObjects().get(worldX + ", " + worldY));
	}*/
}