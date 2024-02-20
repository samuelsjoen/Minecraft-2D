package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.view.GameScreen;
import com.badlogic.gdx.Game;

public class Minecraft2D extends Game {

	@Override
	public void create() {
		this.setScreen(new GameScreen(this));
	}
}
