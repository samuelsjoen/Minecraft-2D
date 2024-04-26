// package com.minecraft.game.view.overlay;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyFloat;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.verify;


// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.minecraft.game.LibgdxUnitTest;
// import com.minecraft.game.model.Health;

// public class HealthViewTest extends LibgdxUnitTest {

//     private SpriteBatch batch;
//     private HealthView healthView;
//     private Health health;

//     @BeforeEach
//     public void setUp() {
//         batch = mock(SpriteBatch.class);
//         health = mock(Health.class);
//         healthView = new HealthView(health, batch);
//     }

//     @AfterEach
//     public void tearDown() {
//         healthView.dispose();
//     }

//     @Test
//     public void testRender() {
//         healthView.render();
//         verify(batch).draw(any(Texture.class), anyFloat(), anyFloat());
//     }

//     @Test
//     public void testDispose() {
//         healthView.dispose();
//         verify(any(Texture.class)).dispose();
//     }
// }
