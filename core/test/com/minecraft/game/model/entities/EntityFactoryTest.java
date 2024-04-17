package com.minecraft.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.Health;
import com.minecraft.game.model.Player;
import com.minecraft.game.utils.Constants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntityFactoryTest {

    @Mock
    private World mockWorld;
    @Mock
    private Player mockPlayer;
    @Mock
    private Health mockHealth;
    @Mock
    private Body mockBody;

    private EntityParams params;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockWorld.createBody(any())).thenReturn(mockBody);
        when(mockBody.getPosition()).thenReturn(new Vector2(100.0f, 200.0f));
        when(mockBody.getWorld()).thenReturn(mockWorld);
        when(mockBody.createFixture(any(FixtureDef.class))).thenReturn(mock(Fixture.class));
        params = new EntityParams(mockWorld, mockPlayer, 100.0f, 200.0f, mockHealth);
    }

    @Test
    void createKnight_CreatesCorrectly() {
        GameEntity entity = EntityFactory.createEntity("Knight", params);
        assertNotNull(entity);
        assertTrue(entity instanceof Knight);
    }

    @Test
    void createSlime_CreatesCorrectly() {
        GameEntity entity = EntityFactory.createEntity("Slime", params);
        assertNotNull(entity);
        assertTrue(entity instanceof Slime);
    }

    @Test
    void createPinkMonster_CreatesCorrectly() {
        GameEntity entity = EntityFactory.createEntity("PinkMonster", params);
        assertNotNull(entity);
        assertTrue(entity instanceof PinkMonster);
    }

    @Test
    void createProjectile_CreatesCorrectly() {
        GameEntity entity = EntityFactory.createEntity("Projectile", params);
        assertNotNull(entity);
        assertTrue(entity instanceof Projectile);
    }

    @Test
    void createEntity_WithUnknownType_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            EntityFactory.createEntity("UnknownType", params);
        });
        assertEquals("Unknown entity type: UnknownType", exception.getMessage());
    }

    @Test
    void createKnight_VerifiesProperties() {
        Knight knight = (Knight) EntityFactory.createEntity("Knight", params);
        assertNotNull(knight);
        assertEquals(100.0f, knight.x);
        assertEquals(200.0f, knight.y);
        assertEquals(2 * Constants.PPM, knight.width);
        assertEquals(4 * Constants.PPM, knight.height);
        assertEquals(mockWorld, knight.getBody().getWorld());
    }

    @Test
    void createSlime_VerifiesProperties() {
        Slime slime = (Slime) EntityFactory.createEntity("Slime", params);
        assertNotNull(slime);
        assertEquals(100.0f, slime.x);
        assertEquals(200.0f, slime.y);
        assertEquals(2 * Constants.PPM, slime.width);
        assertEquals(2 * Constants.PPM, slime.height);
    }

    @Test
    void createPinkMonster_VerifiesProperties() {
        PinkMonster pinkMonster = (PinkMonster) EntityFactory.createEntity("PinkMonster", params);
        assertNotNull(pinkMonster);
        assertEquals(100.0f, pinkMonster.x);
        assertEquals(200.0f, pinkMonster.y);
        assertEquals(2 * Constants.PPM, pinkMonster.width);
        assertEquals(4 * Constants.PPM, pinkMonster.height);
    }

    @Test
    void createProjectile_VerifiesProperties() {
        Projectile projectile = (Projectile) EntityFactory.createEntity("Projectile", params);
        assertNotNull(projectile);
        assertEquals(100.0f, projectile.x);
        assertEquals(200.0f, projectile.y);
        assertEquals(50, projectile.width);
        assertEquals(50, projectile.height);
    }

}
