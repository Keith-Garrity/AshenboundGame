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

    /* regarding character creation*/
    private boolean characterCreation = false;
    private Character player;
    private Texture portrait;
    private String playerName = "";

    private CharacterClass selectedClass = new Knight();
    private Gender selectedGender = Gender.MALE;

    private Button knightButton;
    private Button mageButton;
    private Button rogueButton;
    private Button maleButton;
    private Button femaleButton;
    private Button nameButton;
    private Button createCharacterButton;
    private Button backButton;

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

        /* Landing Page Buttons */
        newGameButton = new Button("New Game", font, 0.5f, 0.28f);
        loadGameButton = new Button("Load Game", font, 0.5f, 0.20f);
        settingsButton = new Button("Settings", font, 0.5f, 0.12f);
        quitButton = new Button("Quit Game", font, 0.5f, 0.04f);

        /*Character Creation Buttons*/
        knightButton = new Button("Knight", font, 0.25f, 0.35f);
        mageButton = new Button("Mage", font, 0.25f, 0.27f);
        rogueButton = new Button("Rogue", font, 0.25f, 0.19f);

        maleButton = new Button("Male", font, 0.65f, 0.35f);
        femaleButton = new Button("Female", font, 0.65f, 0.27f);
        nameButton = new Button("Enter Name", font, 0.65f, 0.19f);
        createCharacterButton = new Button("Create Character", font, 0.5f, 0.08f);
        backButton = new Button("Back", font, 0.08f, 0.08f);

        pauseMenu = new PauseMenu(font);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void updatePortrait() {
        if (portrait != null) {
            portrait.dispose();
        }
        String imagePath = "characters/" + selectedClass.getName().toLowerCase()
            + "_" + selectedGender.name().toLowerCase() + ".png";
        portrait = new Texture(imagePath);
    }

    private void drawCharacterCreation() {
        font.setColor(Color.WHITE);
        font.draw(batch, "CREATE YOUR CHARACTER", 650, 1000);
        font.draw(batch, "Choose Your Class:", 250, 850);
        font.draw(batch, "Choose Your Gender:", 1100, 850);
        font.draw(batch, "Name: " +
                (playerName.isEmpty() ? "Not Selected" : playerName),
            1050, 700);

        // Draw character portrait
        if (portrait != null) {
            batch.draw(portrait, 700, 300, 350, 450);
        }

        knightButton.draw(batch);
        mageButton.draw(batch);
        rogueButton.draw(batch);

        maleButton.draw(batch);
        femaleButton.draw(batch);
        nameButton.draw(batch);
        createCharacterButton.draw(batch);
        backButton.draw(batch);
    }

    private void updateCharacterCreation() {
        if (maleButton.update(viewport)) {
            selectedGender = Gender.MALE;
            updatePortrait();
        }

        if (femaleButton.update(viewport)) {
            selectedGender = Gender.FEMALE;
            updatePortrait();
        }

        if (nameButton.update(viewport)) {
            Gdx.input.getTextInput(
                new Input.TextInputListener() {
                    @Override
                    public void input(String text) {
                        playerName = text.trim();
                    }

                    @Override
                    public void canceled() {
                    }
                },
                "Character Name",
                playerName,
                "Enter your character's name"
            );
        }

        if (createCharacterButton.update(viewport)) {

            if (playerName.isEmpty()) {
                player = new Character(selectedClass, selectedGender);
            } else {
                player = new Character(
                    playerName,
                    selectedClass,
                    selectedGender
                );
            }

            characterCreation = false;
            inGame = true;

            System.out.println("Created character: " + player.getName());
        }

        if (backButton.update(viewport)) {
            characterCreation = false;
            inGame = false;
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.12f, 0.04f, 0.03f, 1f);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        if (characterCreation) {
            updateCharacterCreation();
        } else if (!inGame) {
            if (newGameButton.update(viewport)) {
                characterCreation = true;
                selectedClass = new Knight();
                selectedGender = Gender.MALE;
                playerName = "";
                updatePortrait();
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

            if (knightButton.update(viewport)) {
                selectedClass = new Knight();
                updatePortrait();
            }
            if (mageButton.update(viewport)) {
                selectedClass = new Mage();
                updatePortrait();
            }
            if (rogueButton.update(viewport)) {
                selectedClass = new Rogue();
                updatePortrait();
            }
            if (paused) {
                pauseMenu.draw(batch);
            }
        }
        batch.end();
    }

    @Override /*Deletes Everything at the End */
    public void dispose() {
        batch.dispose();
        image.dispose();
        gameBackground.dispose();
        font.dispose();
        pauseMenu.dispose();
    }
}

