package com.minecraft.game.model;

/**
 * The Score class represents a player's score in the game.
 */
public class Score {

    // Fields
    private int score;

    /**
     * Constructs a new Score object with an initial score of 0.
     */
    public Score() {
        this.score = 0; 
    }

    /**
     * Retrieves the current score.
     * @return The current score.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Adds the points to the current score.
     * @param points The points to add to the score.
     */
    public void addPoints(int points) {
        this.score += points;
    }

    /**
     * Resets the score to 0.
     */
    public void resetScore() {
        this.score = 0;
    }
}
