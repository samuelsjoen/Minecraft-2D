package com.minecraft.game.view.sound;

public interface SoundManager {
    /**
     * Plays the sound.
     */
    void playSound();

    /**
     * Stops the sound.
     */
    void stopSound();

    /**
     * Disposes of the sound resources.
     */
    void dispose();

    /**
     * Returns whether the sound is currently playing.
     * 
     * @return true if the sound is playing, false otherwise
     */
    boolean isPlaying();
}
