package com.minecraft.game.view.overlay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.Player;

public class ScoreView {

    private  BitmapFont font;
    private SpriteBatch batch;

    private float xScore;
    private float yScore;
    private Player player;
    private int score;

    public ScoreView(SpriteBatch batch, Player player){
        //this.font = font;
        this.batch = batch;
        this.player = player;

        this.font = new BitmapFont();

        this.font.setColor(Color.GOLD); // Sets the font color to gold
        this.font.getData().setScale(2f); // Makes the font larger which gives a "bolder" look

    }

    public void render() {
        score = player.getScore(); // Update the score
        font.draw(batch, "Score: " + score, xScore, yScore); // Draw the score on the screen
    }

    public void update(Vector2 lowerLeftCorner) {   
        
        xScore = lowerLeftCorner.x + 255;
        yScore = lowerLeftCorner.y + 695;

    }

    public void dispose() {
        font.dispose();
    }
}