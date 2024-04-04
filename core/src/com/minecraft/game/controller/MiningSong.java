package com.minecraft.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MiningSong implements Runnable {
    private static final String MINING_MUSIC_PATH = "assets/miningSound.mp3";
    private Music music;

    @Override
    public void run() {
        if (music == null) {
            music = Gdx.audio.newMusic(Gdx.files.internal(MINING_MUSIC_PATH));
            music.setLooping(true);
        }
        if (!music.isPlaying()) {
            music.play();
        }
    }

    public void stop() {
        if (music != null && music.isPlaying()) {
            music.stop();
        }
    }

    public void dispose() {
        if (music != null) {
            music.dispose();
            music = null;
        }
    }
}