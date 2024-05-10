package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.Player;
import com.minecraft.game.utils.BodyHelperService;
import com.minecraft.game.utils.Constants;

/**
 * Represents a Projectile entity within the game, extending the GameEntity
 * class.
 * Projectiles are created by the pinkmonster and are designed to
 * interact
 * with the player, causing damage upon collision.
 */
public class Projectile extends GameEntity implements IViewableEntityModel {

    private Vector2 target;
    private float lifeTime = 3.0f; // Time after which the projectile will be destroyed if it doesn't hit the
                                   // target
    private boolean toBeDestroyed = false;
    private boolean markForRemoval = false;

    /**
     * Constructs a Projectile with specified dimensions, initial and target
     * positions.
     * 
     * @param width         The width of the projectile.
     * @param height        The height of the projectile.
     * @param world         The game world where the projectile exists.
     * @param startPosition The initial position of the projectile.
     * @param target        The target position towards which the projectile will
     *                      move.
     */
    public Projectile(float width, float height, World world, Vector2 startPosition, Vector2 target) {
        super(width, height,
                BodyHelperService.createBody(startPosition.x, startPosition.y, width, height, null, false, world,
                        Constants.CATEGORY_ENEMY, Constants.MASK_ENEMY, "projectile", false));
        this.target = target;
        this.body.setBullet(true); // To ensure continuous collision detection
        calculateTrajectory(startPosition, target);
    }

    /**
     * Calculates and applies the trajectory of the projectile based on its start
     * and target positions.
     * 
     * @param startPosition The start position of the projectile.
     * @param target        The target position where the projectile is aimed.
     */
    private void calculateTrajectory(Vector2 startPosition, Vector2 target) {
        float gravity = 0.7f;

        // Calculate horizontal (dx) and vertical (dy) distances to the target
        float dx = target.x - startPosition.x;

        // Decide on an initial angle for the projectile's trajectory
        float angleRadians = 45.0f * (float) Math.PI / 180.0f; // 45 degrees in radians

        // Calculate the initial speed needed to reach the target at the specified angle
        // Simplified formula based on projectile motion equations: v = sqrt(g*h /
        // sin(2*theta))
        float speed = (float) Math.sqrt((gravity * Math.abs(dx)) / Math.sin(2 * angleRadians));

        // Calculate velocity components
        float vx = speed * (float) Math.cos(angleRadians);
        float vy = speed * (float) Math.sin(angleRadians);

        // Apply the velocity to the projectile
        // Adjust vx based on the direction to the target
        Vector2 velocity = new Vector2(dx < 0 ? -vx : vx, vy);
        this.body.setLinearVelocity(velocity);
    }

    /**
     * Updates the state of the projectile, checking for expiration of its lifetime
     * or if it should be destroyed.
     * 
     * @param deltaTime The time elapsed since the last update call.
     */
    @Override
    public void update(float deltaTime) {
        lifeTime -= deltaTime;
        if (lifeTime <= 0 || toBeDestroyed) {
            markForRemoval = true;
        }
    }

    /**
     * Checks for collisions with a player and handles the impact effects.
     * 
     * @param player The player with which the projectile may collide.
     */
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

    /**
     * Marks the projectile for destruction.
     */
    public void markForDestruction() {
        this.toBeDestroyed = true;
    }

    /**
     * Checks if the projectile is marked for removal from the game world.
     * 
     * @return true if the projectile is marked for removal, false otherwise.
     */
    public boolean isMarkedForRemoval() {
        return markForRemoval;
    }

    @Override
    public boolean isFacingRight() {
        // This method is not used as the projectile does not have a facing direction.
        return true;
    }

    @Override
    public float getDeadStateTime() {
        // Not used because projectiles do not have a 'dead' state.
        return 0;
    }

    @Override
    public float getAttack2StateTime() {
        // Not used because projectiles do not have multiple attack phases or states.
        return 0;
    }

    @Override
    public float getStateTime() {
        // Not used because projectiles do not have states that change over time.
        return 0;
    }

    /**
     * Gets the width of the projectile.
     * 
     * @return The width in game units.
     */
    @Override
    public float getWidth() {
        return width;
    }

    /**
     * Gets the height of the projectile.
     * 
     * @return The height in game units.
     */
    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public State getCurrentState() {
        // Not used as projectiles do not utilize the State enumeration.
        return State.IDLE;
    }

    @Override
    public void setAttackFrame(boolean isActive) {
        // Not used because projectiles do not use attack frames.
    }

    /**
     * Sets the projectile's removal status.
     * 
     * @param isMarked true to mark the projectile for removal, false otherwise.
     */
    @Override
    public void setMarkedForRemoval(boolean isMarked) {
        markForRemoval = isMarked;
    }

    /**
     * Gets the current position of the projectile.
     * 
     * @return A Vector2 representing the position of the projectile.
     */
    @Override
    public Vector2 getPosition() {
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }

}
