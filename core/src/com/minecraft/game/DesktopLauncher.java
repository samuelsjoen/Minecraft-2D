package com.minecraft.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.minecraft.game.utils.Constants;

/**
 * The main class for launching the desktop version of the Minecraft game.
 */
public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Minecraft 2D");
		config.setWindowedMode(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		new Lwjgl3Application(new Minecraft(), config);
	}
}
