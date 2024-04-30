package com.minecraft.game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Button {

    private Stage stage;
    private String filepath;
    private float x;
    private float y;
    private String buttonName;

    public Button(String filepath, Stage stage, float x, float y, String buttonName) {
        this.stage = stage;
        this.filepath = filepath;
        this.x = x;
        this.y = y;
        this.buttonName = buttonName;
    }

    public void createButton() {

        // ImageButton
        Texture buttonTexture = new Texture(Gdx.files.internal(this.filepath));
        ImageButton button = new ImageButton(createTextureRegionDrawable(this.filepath));

        float buttonWidth = buttonTexture.getWidth();// / 1.5f;
        float buttonHeight = buttonTexture.getHeight();// / 1.5f;
        
        button.setSize(buttonWidth, buttonHeight);
        /*button.getStyle().imageUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(this.filepath))));
        button.getStyle().imageDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(this.filepath))));*/
    
        button.setPosition(x, y - buttonHeight);
        button.setName(buttonName);

        stage.addActor(button);
    
    }

    private TextureRegionDrawable createTextureRegionDrawable(String filepath) {
        Texture texture = new Texture(Gdx.files.internal(filepath));
        TextureRegion textureRegion = new TextureRegion(texture);
        return new TextureRegionDrawable(textureRegion);
    }

}
