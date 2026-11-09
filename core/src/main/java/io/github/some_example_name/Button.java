package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/** A clickable text button drawn inside a box. Position is a fraction of the screen. */
public class Button {
    private static Texture pixel;   // one shared 1x1 white pixel, used to draw every box

    private static final Color BORDER          = new Color(0.60f, 0.45f, 0.25f, 1f);
    private static final Color BORDER_SELECTED = new Color(1.00f, 0.85f, 0.40f, 1f);
    private static final Color FILL            = new Color(0.10f, 0.08f, 0.12f, 0.92f);
    private static final Color FILL_HOVER      = new Color(0.24f, 0.18f, 0.30f, 0.95f);
    private static final Color FILL_SELECTED   = new Color(0.42f, 0.30f, 0.12f, 0.95f);

    private final String text;
    private final BitmapFont font;
    private final float xFraction;   // 0 = left, 0.5 = center, 1 = right
    private final float yFraction;   // 0 = bottom, 1 = top
    private final GlyphLayout layout = new GlyphLayout();
    private final Vector2 mouse = new Vector2();

    private float boxWidth = 460f;   // every button is the same size unless you change it
    private float boxHeight = 72f;
    private float centerX, centerY;  // middle of the box, in world units
    private float textX, textY;      // where the text is drawn
    private boolean hovering;
    private boolean selected;

    public Button(String text, BitmapFont font, float xFraction, float yFraction) {
        this.text = text;
        this.font = font;
        this.xFraction = xFraction;
        this.yFraction = yFraction;
    }

    public void setBoxSize(float width, float height) {
        this.boxWidth = width;
        this.boxHeight = height;
    }

    /** A selected button is drawn gold (used for the chosen class and gender). */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /** Call every frame. Returns true on the frame the button is clicked. */
    public boolean update(Viewport viewport) {
        layout.setText(font, text);
        centerX = viewport.getWorldWidth() * xFraction;
        centerY = viewport.getWorldHeight() * yFraction;
        textX = centerX - layout.width / 2f;
        textY = centerY + layout.height / 2f;

        mouse.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(mouse);

        // the whole box is clickable, not just the letters
        hovering = mouse.x >= centerX - boxWidth / 2f && mouse.x <= centerX + boxWidth / 2f
            && mouse.y >= centerY - boxHeight / 2f && mouse.y <= centerY + boxHeight / 2f;

        return hovering && Gdx.input.justTouched();
    }

    /** Call between batch.begin() and batch.end(). */
    public void draw(SpriteBatch batch) {
        if (pixel == null) {
            Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pm.setColor(Color.WHITE);
            pm.fill();
            pixel = new Texture(pm);
            pm.dispose();
        }

        float left = centerX - boxWidth / 2f;
        float bottom = centerY - boxHeight / 2f;

        batch.setColor(selected ? BORDER_SELECTED : BORDER);                  // border
        batch.draw(pixel, left - 3, bottom - 3, boxWidth + 6, boxHeight + 6);

        batch.setColor(selected ? FILL_SELECTED : (hovering ? FILL_HOVER : FILL));   // box
        batch.draw(pixel, left, bottom, boxWidth, boxHeight);
        batch.setColor(Color.WHITE);                                          // reset the tint

        font.setColor(selected ? BORDER_SELECTED : (hovering ? Color.YELLOW : Color.WHITE));
        font.draw(batch, text, textX, textY);
    }

    /** Call once from Main.dispose() to free the shared box texture. */
    public static void disposeShared() {
        if (pixel != null) {
            pixel.dispose();
            pixel = null;
        }
    }
}
