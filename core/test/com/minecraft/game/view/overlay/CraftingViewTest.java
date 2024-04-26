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
// import com.minecraft.game.model.crafting.Crafting;

// public class CraftingViewTest extends LibgdxUnitTest {

//     private SpriteBatch batch;
//     private CraftingView craftingView;
//     private Crafting crafting;
//     private BitmapFont font;

//     @BeforeEach
//     public void setUp() {
//         batch = mock(SpriteBatch.class);
//         crafting = mock(Crafting.class);

//         font = mock(BitmapFont.class);
//         BitmapFont.BitmapFontData fontData = mock(BitmapFont.BitmapFontData.class);
//         when(font.getData()).thenReturn(fontData);
//         doNothing().when(fontData).setScale(2);

//         craftingView = new CraftingView(crafting, batch, font);
//     }

//     @AfterEach
//     public void tearDown() {
//         craftingView.dispose();
//     }

//     @Test
//     public void testRender() {
//         when(crafting.isOpen()).thenReturn(true);
//         craftingView.render();
//         verify(crafting, times(1)).isOpen();
//         verify(batch).draw(any(Texture.class), anyFloat(), anyFloat());
//     }

//     @Test
//     public void testDispose() {
//         craftingView.dispose();
//         verify(any(Texture.class)).dispose();
//     }
// }
