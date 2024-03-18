package com.minecraft.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.minecraft.game.model.Inventory;
import com.minecraft.game.model.Item;
import com.minecraft.game.model.Player;
import com.minecraft.game.utils.Constants;

public class CharacterController {

    private Player player;
    private Inventory inventory;
    private int jumpCounter = 0; // Jump counter initialized

    public CharacterController(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
    }

    public void update() {
        float velX = 0;
        if (Player.currentState == Player.State.DEAD) {
            // velX = 0; // if you want the player to stop moving when dead
            if (Gdx.input.isKeyJustPressed(Keys.R)) {
                //Player.getHealth().heal(5);
                //Player.deadStateTime = 0f;
                Player.getHealth().revive();
                player.setCurrentState(Player.State.IDLE);
            }
            return;
        }
        if (Gdx.input.isKeyPressed(Constants.MOVE_RIGHT_KEY)) {
            velX = Constants.PLAYER_MOVE_SPEED;
        }
        if (Gdx.input.isKeyPressed(Constants.MOVE_LEFT_KEY)) {
            velX = -Constants.PLAYER_MOVE_SPEED;
        }
        if (Gdx.input.isKeyJustPressed(Constants.JUMP_KEY) && jumpCounter < 2) {
            float force = player.getBody().getMass() * Constants.PLAYER_JUMP_VELOCITY;
            player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
            player.getBody().applyLinearImpulse(new Vector2(0, force), player.getBody().getPosition(), true);
            jumpCounter++;
        }

        if (Gdx.input.isKeyPressed(Keys.TAB)) {
            if (inventory.getSelectedItem() == Item.WOODEN_SWORD) {
                player.setCurrentState(Player.State.ATTACKING);
                player.attack();

            //player.setCurrentState(Player.State.ATTACKING);
            //player.attack();
            }
        }

        // Check if the player is on the ground to reset the jump counter
        if (player.getBody().getLinearVelocity().y == 0) {
            jumpCounter = 0; // Reset jump counter if player is on the ground
        }
        player.getBody().setLinearVelocity(velX, player.getBody().getLinearVelocity().y);

    }
}
