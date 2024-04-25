package com.minecraft.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

public class CursorUtils {

    private static String currentCursorImagePath; // Store the path of the current cursor image

    /**
     * Set the cursor image to the specified image path
     * @param imagePath The path of the image to set the cursor to
     */
    public static void setCursorPixmap(String imagePath) {
        Pixmap pixmap = new Pixmap(Gdx.files.internal(imagePath));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, 0, 0));
        currentCursorImagePath = imagePath; // Update the current cursor image path
        pixmap.dispose(); // Dispose of the pixmap after setting the cursor
    }

    /**
     * Get the path of the current cursor image
     * @return String object with the path of the current cursor image
     */
    public static String getCurrentCursorImagePath() {
        return currentCursorImagePath;
    }
}
