package com.minecraft.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

public class CursorUtils {

    public static void setCursorPixmap(String imagePath) {
        Pixmap pixmap = new Pixmap(Gdx.files.internal(imagePath));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, 0, 0));
        pixmap.dispose(); // Dispose of the pixmap after setting the cursor
    }
    
}
