package com.minecraft.game.view.sound;

import com.badlogic.gdx.audio.Sound;

public class MineBlockSoundManager implements SoundManager {

    private Sound mineBlock;
    private long id;
    private boolean isPlaying;

    public MineBlockSoundManager(String filePath) {
        this.mineBlock = SoundLoader.loadSoundFile(filePath);
        this.isPlaying = false;
    }

    @Override
    public void playSound() {
        // Play the sound only if it's not currently playing
        if (!isPlaying()) {
            this.id = mineBlock.play(0.5f);
            // Loop the sound
            this.mineBlock.setLooping(this.id, true);
            this.isPlaying = true; 
        }
    }

    @Override
    public void stopSound() {
        // Stop the sound only if it's currently playing
        if (isPlaying()) {
            this.mineBlock.stop(this.id);
            this.isPlaying = false; 
        }
    }

    @Override
    public void dispose() {
        this.mineBlock.dispose();
    }

    @Override
    public boolean isPlaying() {
        return this.isPlaying;
    }

}
