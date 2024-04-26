// package com.minecraft.game.view.overlay;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyFloat;
// import static org.mockito.Mockito.*;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.BitmapFont;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.minecraft.game.LibgdxUnitTest;
// import com.minecraft.game.model.crafting.Inventory;

// public class InventoryViewTest extends LibgdxUnitTest {

//     private SpriteBatch batch;
//     private InventoryView inventoryView;
//     private Inventory inventory;
//     private BitmapFont font;

//     @BeforeEach
//     public void setUp() {
//         batch = mock(SpriteBatch.class);
//         inventory = mock(Inventory.class);
//         font = mock(BitmapFont.class);

//         BitmapFont.BitmapFontData fontData = mock(BitmapFont.BitmapFontData.class);
//         when(font.getData()).thenReturn(fontData);
//         doNothing().when(fontData).setScale(2);

//         inventoryView = new InventoryView(inventory, batch, font);
//     }

//     @AfterEach
//     public void tearDown() {
//         inventoryView.dispose();
//     }

//     @Test
//     public void testRender() {
//         inventoryView.render();
//         verify(batch).draw(any(Texture.class), anyFloat(), anyFloat());
//     }

//     @Test
//     public void testDispose() {
//         inventoryView.dispose();
//         verify(any(Texture.class)).dispose();
//     }
// }
