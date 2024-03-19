package com.minecraft.game;

import com.minecraft.game.controller.MinecraftController;
import com.minecraft.game.model.MinecraftModel;
import com.minecraft.game.model.entities.EntityFactory;
import com.minecraft.game.utils.MinecraftMap;
import com.minecraft.game.view.MinecraftView;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Minecraft extends Game {

	public static Minecraft INSTANCE;
	private int widthScreen, heightScreen;
	public OrthographicCamera camera;
	
	public Minecraft() {
		INSTANCE = this;
	}

	@Override
	public void create() {

		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, widthScreen, heightScreen);

		EntityFactory factory = new EntityFactory();
		MinecraftMap map = new MinecraftMap();
		MinecraftModel model = new MinecraftModel(map, factory);
		MinecraftView view = new MinecraftView(this, model);
		@SuppressWarnings("unused")
		MinecraftController controller = new MinecraftController(model, view);

		//Gdx.input.setInputProcessor(controller); // comment this out to make WorldController "work" placing tiles etc. I don't think we can have multiple input processors

	}

	@Override
    public void render() {
        super.render();
        // Additional game logic can go here
    }

    @Override
    public void dispose() {
        // Dispose of resources when the game is closing
    }
}
