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
    private static final Color BORDER_DISABLED = new Color(0.30f, 0.30f, 0.30f, 1f);
    private static final Color FILL            = new Color(0.10f, 0.08f, 0.12f, 0.92f);
    private static final Color FILL_HOVER      = new Color(0.24f, 0.18f, 0.30f, 0.95f);
    private static final Color FILL_SELECTED   = new Color(0.42f, 0.30f, 0.12f, 0.95f);
    private static final Color FILL_DISABLED   = new Color(0.07f, 0.07f, 0.07f, 0.92f);

    private String text;
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
    private boolean disabled;

    public Button(String text, BitmapFont font, float xFraction, float yFraction) {
        this.text = text;
        this.font = font;
        this.xFraction = xFraction;
        this.yFraction = yFraction;
    }

    public void setText(String text) { this.text = text; }
    public void setBoxSize(float width, float height) { this.boxWidth = width; this.boxHeight = height; }
    public void setSelected(boolean selected) { this.selected = selected; }
    /** A disabled button is greyed out and can't be clicked. */
    public void setDisabled(boolean disabled) { this.disabled = disabled; }
    /** True while the mouse is over the box (also true when disabled). */
    public boolean isHovering() { return hovering; }

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

        return hovering && !disabled && Gdx.input.justTouched();
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

        Color border = disabled ? BORDER_DISABLED : (selected ? BORDER_SELECTED : BORDER);
        Color fill = disabled ? FILL_DISABLED
            : (selected ? FILL_SELECTED : (hovering ? FILL_HOVER : FILL));

        batch.setColor(border);
        batch.draw(pixel, left - 3, bottom - 3, boxWidth + 6, boxHeight + 6);
        batch.setColor(fill);
        batch.draw(pixel, left, bottom, boxWidth, boxHeight);
        batch.setColor(Color.WHITE);                                          // reset the tint

        font.setColor(disabled ? Color.GRAY
            : (selected ? BORDER_SELECTED : (hovering ? Color.YELLOW : Color.WHITE)));
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
