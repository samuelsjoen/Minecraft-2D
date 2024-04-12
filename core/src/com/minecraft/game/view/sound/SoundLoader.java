package com.minecraft.game.view.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundLoader {

    /**
     * Loads a music file from the given path.
     * @param filePath the path to the music file
     * @return the loaded Music object, or null if the music failed to load
     */
    public static Sound loadSoundFile(String filePath) {
        try {
            if (!Gdx.files.internal(filePath).exists()) {
                String fullPath = Gdx.files.internal(filePath).file().getAbsolutePath();
                throw new RuntimeException("Failed to load sound: " + fullPath);
            }
            return Gdx.audio.newSound(Gdx.files.internal(filePath));
        } catch (Exception e) {
            String fullPath = Gdx.files.internal(filePath).file().getAbsolutePath();
            throw new RuntimeException("Failed to load sound: " + fullPath, e);
        }
    }
}
