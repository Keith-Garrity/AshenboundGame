package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private BitmapFont font;
    private GlyphLayout layout;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("ashenbound_title.png");
        font = new BitmapFont();
            font.getData().setScale(3f);      // makes the text bigger
        layout = new GlyphLayout();
    }

    @Override
    public void render() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // where "Start" button sits (in middle, 20% up from bottom)
        layout.setText(font, "Start / Continue");
        float textX = (w - layout.width) / 2f;
        float textY = h * 0.20f;                                     // top edge of the text

        ScreenUtils.clear(0f, 0f, 0f, 1f);               // 1. clear FIRST
        batch.getProjectionMatrix().setToOrtho2D(0, 0, w, h);  // 2. size set
        batch.begin();                                             // 3. begin
        batch.draw(image, 0, 0, w, h);                      // 4. draw
        font.setColor(Color.RED);
        font.draw(batch, "Start / Continue", textX, textY);
        batch.end();

    }

    @Override /** Deletes everything at the end **/
    public void dispose() {
        batch.dispose();
        image.dispose();
        font.dispose();
    }
}
