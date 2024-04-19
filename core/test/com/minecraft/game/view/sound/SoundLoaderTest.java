package com.minecraft.game.view.sound;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.audio.Sound;
import com.minecraft.game.LibgdxUnitTest;

public class SoundLoaderTest extends LibgdxUnitTest {

    private static String filePath;
    private static String invalidFilePath;
    private static Sound testLoadingFile;

    @BeforeAll
    public static void setUp() {
        filePath = "sound/mineSound.wav";
        invalidFilePath = "sound/invalidSound.wav";
    }
    
    @Test
    public void testLoadValidMusic() {
        testLoadingFile = SoundLoader.loadSoundFile(filePath);
        assertNotNull(testLoadingFile);
    }

    @Test
    public void testLoadInvalidMusic() {
        // check if there was thrown a runtimeexception when loading an invalid file
         assertThrows(RuntimeException.class, () -> {
            testLoadingFile = SoundLoader.loadSoundFile(invalidFilePath);
         });
        assertNull(testLoadingFile);
    }
}
