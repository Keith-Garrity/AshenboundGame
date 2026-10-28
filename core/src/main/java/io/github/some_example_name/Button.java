package io.github.some_example_name;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Button {
    private final String text;
    private final BitmapFont font;
    private final float xFraction;   // 0 = left, 0.5 = center, 1 = right
    private final float yFraction;   // 0 = bottom, 1 = top
    private final GlyphLayout layout = new GlyphLayout();
    private final Vector2 mouse = new Vector2();
    private float x, y;
    private boolean hovering;

    public Button(String text, BitmapFont font, float xFraction, float yFraction) {
        this.text = text;
        this.font = font;
        this.xFraction = xFraction;
        this.yFraction = yFraction;
    }

    /** Call every frame. Returns true on the frame the button is clicked. */
    public boolean update(Viewport viewport) {
        layout.setText(font, text);
        x = viewport.getWorldWidth() * xFraction - layout.width / 2f;
        y = viewport.getWorldHeight() * yFraction + layout.height / 2f;

        mouse.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(mouse);

        hovering = mouse.x >= x && mouse.x <= x + layout.width
            && mouse.y >= y - layout.height && mouse.y <= y;

        return hovering && Gdx.input.justTouched();
    }

    /** Call between batch.begin() and batch.end(). */
    public void draw(SpriteBatch batch) {
        font.setColor(hovering ? Color.YELLOW : Color.WHITE);
        font.draw(batch, text, x, y);
    }
}
