package com.minecraft.game.view.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class MineBlockSoundManager implements SoundManager {

    private Sound mineBlock;
    private long id;
    private boolean isPlaying;

    public MineBlockSoundManager() {
        this.mineBlock = Gdx.audio.newSound(Gdx.files.internal("assets/sound/mineBlockSound.wav"));
        this.isPlaying = false;
    }

    @Override
    public void playSound() {
        // Play the sound only if it's not currently playing
        if (!isPlaying) {
            this.id = mineBlock.play(1.0f);
            // Loop the sound
            this.mineBlock.setLooping(this.id, true);
            isPlaying = true; 
        }
    }

    @Override
    public void stopSound() {
        // Stop the sound only if it's currently playing
        if (isPlaying) {
            this.mineBlock.stop(this.id);
            isPlaying = false; 
        }
    }

    @Override
    public void dispose() {
        this.mineBlock.dispose();
    }

}
