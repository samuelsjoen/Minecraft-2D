package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.Minecraft2D;
import com.mygdx.game.utils.Constants;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
// use this on mac in the terminal to run: /usr/bin/env /usr/local/Cellar/openjdk@17/17.0.5/libexec/openjdk.jdk/Contents/Home/bin/java -XstartOnFirstThread @/var/folders/z7/tfjly_9s08z85qmpz7p1vwlc0000gn/T/cp_3qow1l1srat7hvmncgww6msv2.argfile com.mygdx.game.DesktopLauncher

public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Minecraft 2D");
		config.setWindowedMode(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		new Lwjgl3Application(new Minecraft2D(), config);
	}
}
