package com.minecraft.game.utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.minecraft.game.LibgdxUnitTest;

public class CursorUtilsTest extends LibgdxUnitTest {

    @Test
    public void testSetCursorPixmap() {
        CursorUtils.setCursorPixmap("default_cursor.png");
        // assert that newCursor gets called with the correct arguments, and only once
        verify(Gdx.graphics, times(1)).newCursor(any(Pixmap.class), any(Integer.class), any(Integer.class));
    }
}


