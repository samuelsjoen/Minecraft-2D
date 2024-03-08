package com.minecraft.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.utils.BodyHelperService;
import com.minecraft.game.utils.Constants;

public class Projectile extends GameEntity {

    private Texture texture;
    private Vector2 target;
    private float lifeTime = 3.0f; // Time after which the projectile will be destroyed if it doesn't hit the
                                   // target
    private boolean toBeDestroyed = false;
    private boolean markForRemoval = false;

    public Projectile(float width, float height, World world, Vector2 startPosition, Vector2 target) {
        super(width, height, BodyHelperService.createBody(startPosition.x, startPosition.y, width, height, false, world,
                Constants.CATEGORY_ENEMY, Constants.MASK_ENEMY));
        this.texture = new Texture("assets/Rock2.png");
        this.target = target;
        this.body.setBullet(true); // To ensure continuous collision detection
        calculateTrajectory(startPosition, target);
    }

    private void calculateTrajectory(Vector2 startPosition, Vector2 target) {
        Vector2 velocity = new Vector2(target.x - startPosition.x, target.y - startPosition.y);
        // Adjust velocity to change how fast projectile moves
        velocity.scl(0.1f); // Scale the velocity to make it more realistic
        this.body.setLinearVelocity(velocity);
    }

    @Override
    public void update() {
        lifeTime -= Gdx.graphics.getDeltaTime();
        if (lifeTime <= 0 || toBeDestroyed) {
            markForRemoval = true;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (texture != null && !markForRemoval) {
            batch.draw(texture,
                    (body.getPosition().x * Constants.PPM) - 25,
                    (body.getPosition().y * Constants.PPM) - 25,
                    width,
                    height);
        }
    }

    public void markForDestruction() {
        this.toBeDestroyed = true;
    }

    public boolean isMarkedForRemoval() {
        return markForRemoval;
    }

}
