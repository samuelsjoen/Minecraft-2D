package com.minecraft.game.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Input.Keys;
import com.minecraft.game.LibgdxUnitTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerControllerTest extends LibgdxUnitTest {

    @Mock
    private ControllableMinecraftModel mockControllableModel;
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playerController = new PlayerController(mockControllableModel);
    }

    @Test
    void testHandleKeyDown_invalidKey() {
        assertFalse(playerController.handleKeyDown(Keys.K));
        assertFalse(playerController.getMoveLeft());
        assertFalse(playerController.getMoveRight());
        assertFalse(playerController.getIsAttacking());
    }

    @Test
    void testHandleKeyDown_MoveLeft() {
        assertTrue(playerController.handleKeyDown(Keys.A));
        assertTrue(playerController.getMoveLeft());
        assertFalse(playerController.getMoveRight());
        assertFalse(playerController.getIsAttacking());
    }

    @Test
    void testHandleKeyDown_MoveRight() {
        assertTrue(playerController.handleKeyDown(Keys.D));
        assertFalse(playerController.getMoveLeft());
        assertTrue(playerController.getMoveRight());
        assertFalse(playerController.getIsAttacking());
    }

    @Test
    void testHandleKeyDown_PlayerJump() {
        assertTrue(playerController.handleKeyDown(Keys.SPACE));
        verify(mockControllableModel, times(1)).playerJump();
        assertFalse(playerController.getMoveLeft());
        assertFalse(playerController.getMoveRight());
        assertFalse(playerController.getIsAttacking());
    }

    @Test
    void testHandleKeyDown_StartAttacking() {
        assertTrue(playerController.handleKeyDown(Keys.TAB));
        assertTrue(playerController.getIsAttacking());
        assertFalse(playerController.getMoveLeft());
        assertFalse(playerController.getMoveRight());
        verify(mockControllableModel, times(1)).updateMovement(false, false, true);
    }

    @Test
    void testHandleKeyUp_StopAttacking() {
        playerController.setIsAttacking(true);
        assertTrue(playerController.handleKeyUp(Keys.TAB));
        assertFalse(playerController.getIsAttacking());
        assertFalse(playerController.getMoveLeft());
        assertFalse(playerController.getMoveRight());
        verify(mockControllableModel, times(1)).updateMovement(false, false, false);
    }

    @Test
    void testHandleKeyUp_StopMoveLeft() {
        playerController.setMoveLeft(true);
        assertTrue(playerController.handleKeyUp(Keys.A));
        assertFalse(playerController.getMoveLeft());
        assertFalse(playerController.getMoveRight());
        assertFalse(playerController.getIsAttacking());
    }

    @Test
    void testHandleKeyUp_StopMoveRight() {
        playerController.setMoveRight(true);
        assertTrue(playerController.handleKeyUp(Keys.D));
        assertFalse(playerController.getMoveLeft());
        assertFalse(playerController.getMoveRight());
        assertFalse(playerController.getIsAttacking());
    }

    @Test
    void testUpdateMovement() {
        playerController.setMoveLeft(true);
        playerController.setMoveRight(true);
        playerController.setIsAttacking(true);
        playerController.updateMovement();
        verify(mockControllableModel, times(2)).updateMovement(true, true, true);
    }
}