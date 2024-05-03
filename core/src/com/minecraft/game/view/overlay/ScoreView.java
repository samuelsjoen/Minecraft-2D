package com.minecraft.game.view.overlay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.Score;

/**
 * The ScoreView class is responsible for rendering and updating the score display on the screen.
 * It uses a BitmapFont to draw the score and a SpriteBatch to render it.
 * The score is obtained from the Player object and displayed on the screen.
 */
public class ScoreView implements IOverlay {

    private BitmapFont font;    // the font used for rendering
    private SpriteBatch batch;  // the SpriteBatch used for rendering

    private float xScore;       // where the score should be placed on the x-axis
    private float yScore;       // where the score should be placed on the y-axis

    private Score score;        // the score object

    /**
     * Represents a view for displaying the player's score.
     * 
     * @param batch the SpriteBatch used for rendering
     * @param score the Score object which will be displayed
     */
    public ScoreView(SpriteBatch batch, Score score){
        this.batch = batch;
        this.score = score;
        score.resetScore();

        this.font = new BitmapFont();
        this.font.setColor(Color.GOLD);     // Sets the font color to gold
        this.font.getData().setScale(2f);   // Makes the font larger which gives a "bolder" look
    }

    @Override
    public void render() {
        int currentScore = score.getScore(); // Updating the score
        // Drawing the score on the screen based on the new updated coordinated
        font.draw(batch, "Score: " + currentScore, xScore, yScore); 
    }

    @Override
    public void update(Vector2 lowerLeftCorner) {   
        xScore = lowerLeftCorner.x + 255;
        yScore = lowerLeftCorner.y + 695;
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
