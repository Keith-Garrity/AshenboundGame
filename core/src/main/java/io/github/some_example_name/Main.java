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
import com.badlogic.gdx.Input;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Texture gameBackground;
    private BitmapFont font;
    private Viewport viewport;

    private Button newGameButton;
    private Button loadGameButton;
    private Button settingsButton;
    private Button quitButton;

    private boolean inGame = false;   // false = title screen, true = game screen
    private boolean paused = false;
    private PauseMenu pauseMenu;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("ashenbound_title.png");
        gameBackground = new Texture("ashenbound_title_bg.png");
        font = new BitmapFont();
        font.getData().setScale(3f);
        viewport = new FitViewport(1920, 1080);

        newGameButton = new Button("New Game", font, 0.5f, 0.28f);
        loadGameButton = new Button("Load Game", font, 0.5f, 0.20f);
        settingsButton = new Button("Settings", font, 0.5f, 0.12f);
        quitButton = new Button("Quit Game", font, 0.5f, 0.04f);

        pauseMenu = new PauseMenu(font);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.12f, 0.04f, 0.03f, 1f);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        if (!inGame) {
            // title screen buttons
            if (newGameButton.update(viewport)) {
                inGame = true;
                paused = false;
            }
            if (loadGameButton.update(viewport)) {
                System.out.println("Load Game clicked!");
            }
            if (settingsButton.update(viewport)) {
                System.out.println("Settings clicked!");
            }
            if (quitButton.update(viewport)) {
                Gdx.app.exit();
            }
        } else {
            // game screen
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                paused = !paused;                       // the ONLY place Esc is handled
            }
            if (paused) {
                if (pauseMenu.continueButton.update(viewport)) {
                    paused = false;
                }
                if (pauseMenu.saveGameButton.update(viewport)) {
                    System.out.println("Save Game clicked!");
                }
                if (pauseMenu.mainMenuButton.update(viewport)) {
                    paused = false;
                    inGame = false;
                }
                if (pauseMenu.settingsButton.update(viewport)) {
                    System.out.println("Pause Settings clicked!");
                }
            }
        }
        batch.begin();
        if (!inGame) {
         /* ---- Main Menu ------ */
            batch.draw(image, 0, 0, 1920, 1080);
            newGameButton.draw(batch);
            loadGameButton.draw(batch);
            settingsButton.draw(batch);
            quitButton.draw(batch);
        } else {
            /* ----- New Game Screen AS of Now ----- */
            batch.draw(gameBackground, 0, 0, 1920, 1080);
            font.setColor(Color.WHITE);
            font.draw(batch, "Game screen - press Esc to open the pause menu", 60, 1020);

            if (paused) {
                pauseMenu.draw(batch);
            }
        }
        batch.end();
        }
    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        gameBackground.dispose();
        font.dispose();
        pauseMenu.dispose();
    }
    }

