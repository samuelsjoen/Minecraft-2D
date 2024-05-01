package com.minecraft.game.controller;

import com.minecraft.game.view.MinecraftView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.minecraft.game.LibgdxUnitTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

import java.awt.Point;

class BlockControllerTest extends LibgdxUnitTest {

    @Mock
    private ControllableMinecraftModel mockModel;
    @Mock
    private MinecraftView mockView;
    private BlockController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        OrthographicCamera mockCamera = mock(OrthographicCamera.class);
        when(mockView.getCamera()).thenReturn(mockCamera);
        controller = new BlockController(mockModel, mockView);
    }

    @Test
    void testHandleTouchUp() {
        controller.handleTouchUp();

        verify(mockView, times(1)).stopMineBlockSound();
        verifyNoMoreInteractions(mockView);
    }

    @Test
    void testLeftButton() {

        int screenX = 10;
        int screenY = 20;
        int button = Buttons.LEFT;

        controller.handleTouchDown(screenX, screenY, button);

        verify(mockModel, times(1)).isBlockMineable(anyInt(), anyInt());
        verify(mockModel, times(1)).getTileDamage(anyInt(), anyInt());
    }

    @Test
    void testRightButton() {
        int screenX = 10;
        int screenY = 20;
        int button = Buttons.RIGHT;

        controller.handleTouchDown(screenX, screenY, button);

        verify(mockModel, times(1)).addBlock(anyInt(), anyInt());
        verifyNoMoreInteractions(mockModel);
    }

    @Test
    void testInvalidButton() {
        int screenX = 10;
        int screenY = 20;
        int button = 123; // Invalid button

        controller.handleTouchDown(screenX, screenY, button);

        verifyNoInteractions(mockModel);
    }

    @Test
    void testInvalidTileCoordinates() {
        // Create a spy of the real controller
        BlockController spyController = spy(controller);

        // when tileX < 0 or tileY < 0
        int screenX = 10;
        int screenY = 20;
        int button = Buttons.LEFT;

        // Override the calculateTileXAndY method to return (-1, -1)
        doReturn(new Point(-1, -1)).when(spyController).calculateTileXAndY(screenX, screenY);

        boolean result = spyController.handleTouchDown(screenX, screenY, button);

        assertFalse(result);
    }

    @Test
    void testPlayingMineBlockSound() {

        int screenX = 10;
        int screenY = 20;
        int button = Buttons.LEFT;

        when(mockModel.isBlockMineable(anyInt(), anyInt())).thenReturn(true);

        controller.handleTouchDown(screenX, screenY, button);

        verify(mockView, times(1)).playMineBlockSound();

    }

    @Test
    void testLongPressRemovesBlock() {
        int screenX = 10;
        int screenY = 20;
        int button = Buttons.LEFT;

        // Simulate long press by calling handleTouchDown multiple times
        for (int i = 0; i < 1000; i++) {
            controller.handleTouchDown(screenX, screenY, button);
        }

        // Verify that removeBlock was called
        verify(mockModel, times(1)).removeBlock(anyInt(), anyInt());
        verify(mockView, times(1)).stopMineBlockSound();

    }

}
