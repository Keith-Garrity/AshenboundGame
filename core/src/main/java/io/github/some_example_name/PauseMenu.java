package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

/* The pause menu: dimmed screen, a small panel, a title, and four buttons. */
/* The goal is to have a background of a little scroll but so far its a little panel */

public class PauseMenu {
    public Button continueButton;
    public Button saveGameButton;
    public Button mainMenuButton;
    public Button settingsButton;

    private BitmapFont font;
    private Texture blackBox;

    public PauseMenu(BitmapFont font) {
        this.font = font;

        continueButton = new Button("Continue", font, 0.5f, 0.60f);
        saveGameButton = new Button("Save Game", font, 0.5f, 0.52f);
        mainMenuButton = new Button("Main Menu", font, 0.5f, 0.44f);
        settingsButton = new Button("Settings", font, 0.5f, 0.36f);

        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(Color.BLACK);
        pm.fill();
        blackBox = new Texture(pm);
        pm.dispose();
    }
    public void draw(SpriteBatch batch) {
        batch.draw(blackBox, 660, 330, 600, 430);     // left, bottom, width, height

        font.setColor(Color.WHITE);
        font.draw(batch, "PAUSED", 0, 725, 1920, Align.center, false);

        continueButton.draw(batch);
        saveGameButton.draw(batch);
        mainMenuButton.draw(batch);
        settingsButton.draw(batch);
    }

    public void dispose() {
        blackBox.dispose();
    }
}
