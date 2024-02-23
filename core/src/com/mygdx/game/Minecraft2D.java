package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.utils.Constants;
import com.mygdx.game.view.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Minecraft2D extends Game {
	public OrthographicCamera camera;
	public static Minecraft2D INSTANCE;
	private int widthScreen, heightScreen;
	private OrthographicCamera orthographicCamera;

	public Minecraft2D() {
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