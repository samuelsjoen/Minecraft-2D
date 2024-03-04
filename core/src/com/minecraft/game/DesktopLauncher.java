package com.minecraft.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.minecraft.game.utils.Constants;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument

// cd /Users/martine/Desktop/team6 ; /usr/bin/env /usr/local/Cellar/openjdk@17/17.0.5/libexec/openjdk.jdk/Contents/Home/bin/java -XstartOnFirstThread @/var/folders/z7/tfjly_9s08z85qmpz7p1vwlc0000gn/T/cp_acpu49xneo737uq9e5khdlkds.argfile com.minecraft.game.DesktopLauncher

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Minecraft 2D");
		config.setWindowedMode(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		new Lwjgl3Application(new Minecraft(), config);
	}
}
