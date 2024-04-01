package com.minecraft.game.view.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.graphics.g2d.TextureRegion;
// import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.minecraft.game.model.entities.Projectile;
import com.minecraft.game.utils.Constants;

public class ProjectileRenderer implements EntityRenderer<Projectile> {
    private Texture sheet;
    // private static float stateTime;

    public ProjectileRenderer() {
        sheet = new Texture("assets/Rock2.png");
    }

    @Override
    public void render(Projectile entity, SpriteBatch batch) {
        if (sheet != null && !Projectile.isMarkedForRemoval()) {
            batch.draw(sheet,
                    (entity.getBody().getPosition().x * Constants.PPM) - 25,
                    (entity.getBody().getPosition().y * Constants.PPM) - 25,
                    entity.width,
                    entity.height);
        }

        // stateTime += Gdx.graphics.getDeltaTime(); // Assuming stateTime is reset
        // elsewhere as needed
    }

    public void dispose() {
        sheet.dispose();
    }
}
