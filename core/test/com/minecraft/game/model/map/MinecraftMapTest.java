package com.minecraft.game.model.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.minecraft.game.LibgdxUnitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class MinecraftMapTest extends LibgdxUnitTest {

    private MinecraftMap tileMapHelper;
	private MinecraftMap tileMapHelperMock;
    private TiledMap tiledMap;
	private TiledMap tiledMapMock;


	@BeforeEach
	void setUp() {
		// mocking
		tileMapHelperMock = mock(MinecraftMap.class);
	
		tiledMapMock = mock(TiledMap.class);

		// load the map
		String mapPath = "../assets/map/mapExample2-64.tmx";
        tiledMap = MapLoader.loadTileMap(mapPath);

		// create instance of TileMapHelper
		//tileMapHelper = new MinecraftMap(gameScreenMock);
		tileMapHelper = new MinecraftMap();
	}

	@Test
	public void testSetupMap() {
	}

	@Test
	public void removeBlock() {
		TiledMapTileLayer tiledMapLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
		// There is placed a bedrock tile at the cooordiantes 0,0, so it shouldn't be null
		System.out.println(tiledMapLayer.getCell(0, 0).getTile().getId());
		assertNotNull(tiledMapLayer.getCell(0, 0));
		tileMapHelper.removeBlock(0, 0, tiledMap);
		// The bedrock tile should be removed
		assertNull(tiledMapLayer.getCell(0, 0));
	}

	// ?????
	@Test
	public void getTiledMapTest() {
		// stubbing
		when(tileMapHelperMock.getTiledMap()).thenReturn(tiledMapMock);
		// when
		@SuppressWarnings("unused")
		TiledMap tiledMap = tileMapHelperMock.getTiledMap();
		// then
		verify(tileMapHelperMock).getTiledMap();
	}


}
