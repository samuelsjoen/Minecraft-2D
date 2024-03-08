package com.minecraft.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.minecraft.game.view.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Minecraft extends Game {
	public OrthographicCamera camera;
	public static Minecraft INSTANCE;
	private int widthScreen, heightScreen;
	public OrthographicCamera orthographicCamera;

	public Minecraft() {
		INSTANCE = this;
	}

	@Override
	public void create() {
		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.orthographicCamera = new OrthographicCamera();
		this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
		setScreen(new MenuScreen(this));
	}
}