package com.minecraft.game;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.badlogic.gdx.backends.headless.HeadlessNativesLoader;
import com.badlogic.gdx.backends.headless.mock.audio.MockAudio;
import com.badlogic.gdx.backends.headless.mock.input.MockInput;
import com.badlogic.gdx.graphics.GL20;

public class LibgdxUnitTest {

    private static Application app;

    @BeforeAll
    public static void init() {
        app = new HeadlessApplication(new ApplicationAdapter() {
        });

        HeadlessNativesLoader.load();
        Gdx.files = new HeadlessFiles();
        // Mock some of the Gdx methods
        Gdx.graphics =  mock(Graphics.class);
        Gdx.input = new MockInput();
        Gdx.audio = new MockAudio();

        Gdx.gl = mock(GL20.class);
        Gdx.gl20 = Gdx.gl;
    }

    @AfterAll
    public static void cleanUp() {
        app.exit();
        app = null;
    }

}
