package com.minecraft.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ScreenUtils;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.view.GameScreen;
import com.minecraft.game.view.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Minecraft extends Game {
	public OrthographicCamera camera;
	public static Minecraft INSTANCE;
	private int widthScreen, heightScreen;
	public OrthographicCamera orthographicCamera;

	private TiledMap tiledMap;


	public Minecraft() {
		INSTANCE = this;
	}

	@Override
	public void create() {
		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.orthographicCamera = new OrthographicCamera();
		this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
		
		//setScreen(new GameScreen(orthographicCamera));
		setScreen(new MenuScreen(this));

		tiledMap = new TmxMapLoader().load("assets/mapExample2-64.tmx");

		// Access the tile layers
		MapLayer layer = tiledMap.getLayers().get("mineable");
        //for (MapLayer layer : tiledMap.getLayers()) {
			//System.out.println("Layer is: " + layer.getName());
            if (layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tiledLayer = (TiledMapTileLayer) layer;

                // Iterate through each cell of the layer
                for (int y = 0; y < tiledLayer.getHeight(); y++) {
                    for (int x = 0; x < tiledLayer.getWidth(); x++) {
                        TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                        if (cell != null) {
                            // Get the tile ID of the cell
                            int tileId = cell.getTile().getId();
                            // Do something with the tile ID, for example, print it
                            System.out.println("Tile at (" + x + ", " + y + ") has ID: " + tileId);
                        }
                    }
                }
            }
        }

}

/*package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.view.GameScreen;
import com.mygdx.game.view.MenuScreen;
import com.badlogic.gdx.Game;

public class Minecraft2D extends Game {

	@Override
	public void create() {
		//this.setScreen(new GameScreen(this));
		this.setScreen(new MenuScreen(this));

	}
}
*/




/*package com.minecraft.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Minecraft extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("assets/badlogic.jpg");
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}*/
