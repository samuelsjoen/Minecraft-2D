package com.minecraft.game.view.sound;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.minecraft.game.LibgdxUnitTest;

public class MineBlockSoundManagerTest extends LibgdxUnitTest {

    private MineBlockSoundManager soundManager;

    @BeforeEach
    public void setUp() {
        soundManager = new MineBlockSoundManager("sound/mineSound.wav");
    }

    @AfterEach
    public void tearDown() {
        soundManager.dispose();
    }

    @Test
    public void testPlaySound() {
        assertFalse(soundManager.isPlaying());
        soundManager.playSound();
        assertTrue(soundManager.isPlaying());
    }

    @Test
    public void testStopSound() {
        soundManager.playSound();
        assertTrue(soundManager.isPlaying());
        soundManager.stopSound();
        assertFalse(soundManager.isPlaying());
    }

    @Test
    public void testIsSoundPlaying() {
        assertFalse(soundManager.isPlaying());
        soundManager.playSound();
        assertTrue(soundManager.isPlaying());
        soundManager.stopSound();
        assertFalse(soundManager.isPlaying());
    }
}
