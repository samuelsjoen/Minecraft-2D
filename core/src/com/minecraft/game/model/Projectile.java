package com.minecraft.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.utils.BodyHelperService;
import com.minecraft.game.utils.Constants;

public class Projectile extends GameEntity {

    private Texture texture;
    @SuppressWarnings("unused")
    private Vector2 target;
    private float lifeTime = 3.0f; // Time after which the projectile will be destroyed if it doesn't hit the
                                   // target
    private boolean toBeDestroyed = false;
    private boolean markForRemoval = false;

    public Projectile(float width, float height, World world, Vector2 startPosition, Vector2 target) {
        super(width, height, BodyHelperService.createBody(startPosition.x, startPosition.y, width, height, null, false, world,
                Constants.CATEGORY_ENEMY, Constants.MASK_ENEMY, "projectile", false));
        this.texture = new Texture("assets/Rock2.png");
        this.target = target;
        this.body.setBullet(true); // To ensure continuous collision detection
        calculateTrajectory(startPosition, target);
    }

    private void calculateTrajectory(Vector2 startPosition, Vector2 target) {
        float gravity = 0.7f; // Adjust based on your game's gravity scale

        // Calculate horizontal (dx) and vertical (dy) distances to the target
        float dx = target.x - startPosition.x;
        // float dy = target.y - startPosition.y;

        // Decide on an initial angle for the projectile's trajectory
        float angleRadians = 45.0f * (float) Math.PI / 180.0f; // 45 degrees in radians

        // Calculate the initial speed needed to reach the target at the specified angle
        // Simplified formula based on projectile motion equations: v = sqrt(g*h /
        // sin(2*theta))
        // Adjusted to incorporate horizontal distance (dx) and to fine-tune for game
        // feel
        float speed = (float) Math.sqrt((gravity * Math.abs(dx)) / Math.sin(2 * angleRadians));

        // Calculate velocity components
        float vx = speed * (float) Math.cos(angleRadians);
        float vy = speed * (float) Math.sin(angleRadians);

        // Apply the velocity to the projectile
        // Adjust vx based on the direction to the target
        Vector2 velocity = new Vector2(dx < 0 ? -vx : vx, vy);
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

    public void checkCollisionWithPlayer(Player player) {
        float distanceThreshold = 70f; // Distance required between player and coordinates
        Vector2 playerPosition = new Vector2(player.getBody().getPosition().x * Constants.PPM,
                player.getBody().getPosition().y * Constants.PPM);
        Vector2 projectilePosition = new Vector2(body.getPosition().x * Constants.PPM,
                body.getPosition().y * Constants.PPM);
        if (playerPosition.dst(projectilePosition) < distanceThreshold) {
            player.getHit();
            this.markForDestruction();
        }
    }

    public void markForDestruction() {
        this.toBeDestroyed = true;
    }

    public boolean isMarkedForRemoval() {
        return markForRemoval;
    }

}
