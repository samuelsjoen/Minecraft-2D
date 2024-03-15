package com.mygdx.game.utils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.minecraft.game.view.GameScreen;
import com.mygdx.game.LibgdxUnitTest;
import com.minecraft.game.Minecraft;
import com.minecraft.game.utils.TileMapHelper;
import com.minecraft.game.utils.TileMapLoader;
import com.minecraft.game.utils.TileType;
// headless backend, dependancy: com.badlogicgames.gdx:gdx-backend-headless:$gdxVersion
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TileMapHelperTest extends LibgdxUnitTest {

    private TileMapHelper tileMapHelper;
	private TileMapHelper tileMapHelperMock;
    private GameScreen gameScreenMock;
    private TiledMap tiledMap;
	private TiledMap tiledMapMock;

    /**
	 * Static method run before everything else
	 */
	@BeforeEach
	void setUp() {
		// mocking
		gameScreenMock = mock(GameScreen.class);
		tileMapHelperMock = mock(TileMapHelper.class);
	
		tiledMapMock = mock(TiledMap.class);

		// load the map
		String mapPath = "../assets/map/mapExample2-64.tmx";
        tiledMap = TileMapLoader.loadTileMap(mapPath);

		// create instance of TileMapHelper
		tileMapHelper = new TileMapHelper(gameScreenMock);
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
		TiledMap tiledMap = tileMapHelperMock.getTiledMap();
		// then
		verify(tileMapHelperMock).getTiledMap();
	}


}