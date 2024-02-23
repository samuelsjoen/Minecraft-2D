package com.minecraft.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.minecraft.game.utils.Constants;
import com.minecraft.game.view.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Minecraft extends Game {
	public OrthographicCamera camera;
	public static Minecraft INSTANCE;
	private int widthScreen, heightScreen;
	private OrthographicCamera orthographicCamera;

	public Minecraft() {
		INSTANCE = this;
	}

	@Override
	public void create() {
		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.orthographicCamera = new OrthographicCamera();
		this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
		setScreen(new GameScreen(orthographicCamera));
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
