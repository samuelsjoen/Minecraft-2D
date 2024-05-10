package com.minecraft.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreTest {

    private Score score;

    @BeforeEach
    public void setUp() {
        score = new Score();
    }

    @Test
    void testAddScore() {
        // Initial score should be 0
        assertEquals(0, score.getScore(), "Initial score should be 0.");

        // Add 10 points to the score
        score.addPoints(10);
        assertEquals(10, score.getScore(), "Score should be 10 after adding 10 points.");

        // Add another 5 points
        score.addPoints(5);
        assertEquals(15, score.getScore(), "Score should be 15 after adding an additional 5 points.");
    }

    @Test
    void testGetScore() {
        // Set the score to 20
        score.addPoints(20);
        // Check that getScore returns the correct value
        assertEquals(20, score.getScore(), "getScore should return the current score of 20.");
    }
    
}
