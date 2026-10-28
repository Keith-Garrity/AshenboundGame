package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private BitmapFont font;
    private Viewport viewport;

    private Button newGameButton;
    private Button loadGameButton;
    private Button settingsButton;
    private Button quitButton;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("ashenbound_title.png");
        font = new BitmapFont();
        font.getData().setScale(3f);
        viewport = new FitViewport(1920, 1080);

        newGameButton  = new Button("New Game",  font, 0.5f, 0.28f);
        loadGameButton = new Button("Load Game", font, 0.5f, 0.20f);
        settingsButton = new Button("Settings",  font, 0.5f, 0.12f);
        quitButton = new Button("Quit Game",  font, 0.5f, 0.04f);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
    @Override
    public void render() {
        ScreenUtils.clear(0.12f, 0.04f, 0.03f, 1f);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        if (newGameButton.update(viewport))
            { System.out.println("New Game clicked!"); }
        if (loadGameButton.update(viewport))
            { System.out.println("Load Game clicked!"); }
        if (settingsButton.update(viewport))
            { System.out.println("Settings clicked!"); }
        if (quitButton.update(viewport)) {
            Gdx.app.exit();
        }
        batch.begin();
        batch.draw(image, 0, 0, 1920, 1080);
        newGameButton.draw(batch);
        loadGameButton.draw(batch);
        settingsButton.draw(batch);
        quitButton.draw(batch);
        batch.end();

    }

    @Override /** Deletes everything at the end **/
    public void dispose() {
        batch.dispose();
        image.dispose();
        font.dispose();
    }
}
