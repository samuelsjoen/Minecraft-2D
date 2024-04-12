package com.minecraft.game.view.sound;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.audio.Sound;
import com.minecraft.game.LibgdxUnitTest;

public class MusicLoaderTest extends LibgdxUnitTest {

    private static String filePath;
    private static String invalidFilePath;
    private static Sound testLoadingFile;

    @BeforeAll
    public static void setUp() {
        filePath = "assets/sound/mineSound.wav";
        invalidFilePath = "assets/sound/invalidSound.wav";
    }
    
    @Test
    public void testLoadValidMusic() {
        testLoadingFile = SoundLoader.loadSoundFile(filePath);
        assertNotNull(testLoadingFile);
    }

    // Goes through even though it should not...
    @Test
    public void testLoadInvalidMusic() {
        testLoadingFile = SoundLoader.loadSoundFile(invalidFilePath);
        // assertNull(invalidFilePath);
        // check if there was thrown a runtimeexception when loading an invalid file
        // assertThrows(RuntimeException.class, () -> {
        //     SoundLoader.loadSoundFile("assets/sound/invalidSound.wav");
        // });
        System.out.println(testLoadingFile.toString());
    }



}
