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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;

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
    private Button randomNameButton;
    private Button beginAdventureButton;

    private Enemy enemy;
    private Texture enemyTexture;
    private boolean inCombat = false;
    private Combat combat = null;
    private Button combatDoneButton;
    private Button[] abilityButtons = new Button[4];

    private Button newGameButton;
    private Button loadGameButton;
    private Button settingsButton;
    private Button quitButton;

    private boolean inGame = false;   // false = title screen, true = game screen
    private boolean paused = false;
    private PauseMenu pauseMenu;
    private boolean typingName = false;

    private Texture pixel;
    private boolean characterOverview = false;


    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("ashenbound_title.png");
        gameBackground = new Texture("ashenbound_title_bg.png");

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        pixel = new Texture(pixmap);
        pixmap.dispose();

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

        maleButton = new Button("Male", font, 0.75f, 0.35f);
        femaleButton = new Button("Female", font, 0.75f, 0.27f);
        nameButton = new Button("Enter Name", font, 0.75f, 0.19f);
        createCharacterButton = new Button("Create Character", font, 0.5f, 0.08f);
        backButton = new Button("Back", font, 0.08f, 0.08f);
        typingName = false;
        randomNameButton = new Button("Random Name", font, 0.75f, 0.11f);
        beginAdventureButton = new Button("Begin Adventure", font, 0.5f, 0.11f);
        beginAdventureButton.setBoxSize(400, 80);

        combatDoneButton = new Button("Continue", font, 0.5f, 0.12f);
        combatDoneButton.setBoxSize(360, 72);

        /* Attack Screen */
        for (int i = 0; i < abilityButtons.length; i++) {
            abilityButtons[i] = new Button("", font, 0.2f + i * 0.2f, 0.12f);   // spread across the bottom
            abilityButtons[i].setBoxSize(360, 72);
        }

        pauseMenu = new PauseMenu(font);
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyDown(int keycode) {
                if (!typingName) return false;
                if (keycode == Input.Keys.BACKSPACE && !playerName.isEmpty()) {
                    playerName = playerName.substring(0, playerName.length() - 1);
                    return true;
                }
                if (keycode == Input.Keys.ENTER || keycode == Input.Keys.ESCAPE) {
                    typingName = false;                  // Enter or Esc finishes typing
                    return true;
                }
                return false;
            }
            public boolean keyTyped(char c) {
                if (!typingName) return false;
                if (c >= 32 && c < 127 && playerName.length() < 16) {   // letters, numbers, spaces; max 16
                    playerName += c;
                    return true;
                }
                return false;
            }
        });
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
        System.out.println("Loading portrait: " + imagePath);     // DEBUG
        portrait = new Texture(imagePath);
    }

    private void drawCharacterCreation() {
        font.setColor(Color.WHITE);
        font.draw(batch, "CREATE YOUR CHARACTER", 650, 1000);
        font.draw(batch, "Choose Your Class:", 250, 850);
        font.draw(batch, "Choose Your Gender:", 1100, 850);
        String cursor = (typingName && (System.currentTimeMillis() / 500) % 2 == 0) ? "_" : "";
        font.setColor(Color.WHITE);
        font.draw(batch, "Name: " + playerName + cursor, 0, 270, 1920, Align.center, false);
        // Draw character portrait
        if (portrait != null) {
            batch.draw(portrait, 725, 300, 300, 450);
        }
        knightButton.draw(batch);
        mageButton.draw(batch);
        rogueButton.draw(batch);
        randomNameButton.draw(batch);

        maleButton.draw(batch);
        femaleButton.draw(batch);
        nameButton.draw(batch);
        createCharacterButton.draw(batch);
        backButton.draw(batch);
    }

    private void updateCharacterCreation() {
        nameButton.setSelected(typingName);
        knightButton.setSelected(selectedClass instanceof Knight);
        mageButton.setSelected(selectedClass instanceof Mage);
        rogueButton.setSelected(selectedClass instanceof Rogue);
        maleButton.setSelected(selectedGender == Gender.MALE);
        femaleButton.setSelected(selectedGender == Gender.FEMALE);
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
        if (maleButton.update(viewport)) {
            selectedGender = Gender.MALE;
            updatePortrait();
        }
        if (femaleButton.update(viewport)) {
            selectedGender = Gender.FEMALE;
            updatePortrait();
        }
        if (nameButton.update(viewport)) {
            typingName = !typingName;                    // click to start or stop typing
        }
        if (randomNameButton.update(viewport)) {
            playerName = NameGenerator.randomName(selectedGender);
            typingName = false;
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
            characterOverview = true;
            inGame = false;
            paused = false;

            System.out.println("Created character: " + player.getName());
        }

        if (backButton.update(viewport)) {
            characterCreation = false;
            inGame = false;
        }
    }

    private void drawCharacterOverview() {
        batch.setColor(Color.BLACK);
        batch.draw(pixel, 0, 0, 1920, 1080);
        batch.setColor(Color.WHITE);
        // Make text smaller for the overview screen
        font.getData().setScale(2f);
        // Character portrait
        if (portrait != null) {
            batch.draw(portrait, 150, 300, 400, 550);
        }
        // Character information
        font.setColor(Color.GOLD);
        font.draw(batch, "CHARACTER OVERVIEW", 700, 950);
        font.setColor(Color.WHITE);
        font.draw(batch, "Name: " + player.getName(), 700, 870);
        font.draw(batch, "Class: "
            + player.getCharacterClass().getName(), 700, 820);
        font.draw(batch, "Level: " + player.getLevel(), 700, 770);
        font.draw(batch, "Health: " + player.getHealth()
            + "/" + player.getMaxHealth(), 700, 720);
        // Class description
        font.setColor(Color.GOLD);
        font.draw(batch, "CLASS DESCRIPTION", 700, 650);
        font.setColor(Color.WHITE);
        GlyphLayout descriptionLayout = new GlyphLayout();
        descriptionLayout.setText(
            font,
            player.getCharacterClass().getDescription(),
            Color.WHITE,
            1050,
            Align.left,
            true
        );
        font.draw(batch, descriptionLayout, 700, 610);
        // Unique class ability
        font.setColor(Color.GOLD);
        font.draw(batch, "SPECIAL ABILITY", 700, 500);
        font.setColor(Color.WHITE);
        GlyphLayout specialAbilityLayout = new GlyphLayout();
        specialAbilityLayout.setText(
            font,
            player.getCharacterClass().getSpecialAbility(),
            Color.WHITE,
            1050,
            Align.left,
            true
        );
        font.draw(batch, specialAbilityLayout, 700, 465);
        // Class abilities
        font.setColor(Color.GOLD);
        font.draw(batch, "CLASS ABILITIES", 700, 390);
        // Display abilities in two columns
        int abilityCount = player.getCharacterClass().getAbilities().size();
        for (int i = 0; i < abilityCount; i++) {
            Ability ability = player.getCharacterClass().getAbilities().get(i);
            // Determine the ability's column and row
            int column = i % 2;
            int row = i / 2;
            float x = 700 + column * 450;
            float y = 330 - row * 100;
            // Ability name
            font.setColor(Color.GOLD);
            font.draw(batch, ability.getName(), x, y);
            // Ability effects
            String effect = "";
            if (ability.getDamage() > 0) {
                effect += "Damage: " + ability.getDamage() + "  ";
            }
            if (ability.getBlock() > 0) {
                effect += "Block: " + ability.getBlock() + "  ";
            }
            if (ability.getHeal() > 0) {
                effect += "Healing: " + ability.getHeal() + "  ";
            }
            if (ability.getCooldown() > 0) {
                effect += "Cooldown: " + ability.getCooldown();
            }
            font.setColor(Color.WHITE);
            GlyphLayout effectLayout = new GlyphLayout();
            effectLayout.setText(
                font,
                effect,
                Color.WHITE,
                480,
                Align.left,
                true
            );
            font.draw(batch, effectLayout, x, y - 35);
        }
        // Begin adventure button
        beginAdventureButton.draw(batch);
        // Restore original font size
        font.getData().setScale(3f);
        font.setColor(Color.WHITE);
    }

    private void startCombat(Enemy enemy) {
        // Initialize combat FIRST
        combat = new Combat(player, enemy);
        // Load enemy image
        enemyTexture = new Texture(enemy.getImagePath());
        // Switch to combat screen LAST
        inCombat = true;
        System.out.println("Combat started against " + enemy.getName());
    }

    private void drawHealthBar(float x, float y, float width, float height,
                               int currentHealth, int maxHealth, Color barColor) {
        float percentage = Math.max(0f,
            Math.min(1f, (float) currentHealth / maxHealth));
        // Background of health bar
        batch.setColor(Color.DARK_GRAY);
        batch.draw(pixel, x, y, width, height);
        // Current health
        batch.setColor(barColor);
        batch.draw(pixel, x, y, width * percentage, height);
        batch.setColor(Color.WHITE);
    }

    private void drawPanel(float x, float y, float width, float height) {
        batch.setColor(0.04f, 0.05f, 0.09f, 0.88f);
        batch.draw(pixel, x, y, width, height);
        batch.setColor(Color.WHITE);
    }

    private void drawCombat() {
        if (combat == null) {
            return;
        }
        Enemy enemy = combat.getEnemy();
        // Background
        batch.draw(gameBackground, 0, 0, 1920, 1080);
        // Dark overlay
        batch.setColor(0.02f, 0.02f, 0.05f, 0.65f);
        batch.draw(pixel, 0, 0, 1920, 1080);
        batch.setColor(Color.WHITE);
        // Title
        font.setColor(Color.GOLD);
        font.draw(batch, "BATTLE - TURN " + combat.getTurn(),
            0, 1010, 1920, Align.center, false);
        // Character panels
        drawPanel(120, 280, 650, 650);
        drawPanel(1150, 280, 650, 650);
        // Player portrait
        if (portrait != null) {
            batch.draw(portrait, 275, 390, 340, 450);
        }
        // Enemy artwork
        if (enemyTexture != null) {
            batch.draw(enemyTexture, 1310, 390, 340, 450);
        }
        // Player information
        font.setColor(Color.WHITE);
        font.draw(batch, player.getName(), 120, 875, 650,
            Align.center, false);
        font.draw(batch, "HP: " + player.getHealth() + "/"
            + player.getMaxHealth(), 170, 355);
        drawHealthBar(170, 290, 550, 22,
            player.getHealth(), player.getMaxHealth(), Color.GREEN);
        font.draw(batch, "BLOCK: " + combat.getPlayerBlock(),
            170, 290);
        // Enemy information
        font.setColor(Color.WHITE);
        font.draw(batch, enemy.getName(), 1150, 875, 650,
            Align.center, false);
        font.draw(batch, "HP: " + enemy.getHealth() + "/"
            + enemy.getMaxHealth(), 1200, 355);
        drawHealthBar(1200, 290, 550, 22,
            enemy.getHealth(), enemy.getMaxHealth(), Color.RED);
        // Enemy intent
        if (!combat.isOver()) {
            drawPanel(650, 790, 620, 90);
            font.setColor(Color.ORANGE);
            font.draw(batch, "ENEMY INTENT", 650, 855, 620,
                Align.center, false);
            font.setColor(Color.WHITE);
            font.draw(batch, combat.getEnemyIntent(), 650, 815, 620,
                Align.center, false);
        }
        // Combat log
        drawPanel(650, 430, 620, 250);
        font.setColor(Color.GOLD);
        font.draw(batch, "COMBAT LOG", 650, 650, 620,
            Align.center, false);
        font.setColor(Color.WHITE);
        float logY = 600;
        for (String line : combat.getRecentLog()) {
            font.draw(batch, line, 670, logY, 580,
                Align.center, false);
            logY -= 55;
        }
        // Ability buttons
        if (!combat.isOver()) {
            int abilityCount = Math.min(
                abilityButtons.length,
                combat.getAbilities().size()
            );
            for (int i = 0; i < abilityCount; i++) {
                abilityButtons[i].draw(batch);
            }
        }
        // Victory or defeat
        if (combat.isOver()) {
            drawPanel(600, 430, 720, 220);
            font.setColor(combat.playerWon() ? Color.GOLD : Color.RED);
            font.draw(batch,
                combat.playerWon() ? "VICTORY!" : "DEFEAT",
                600, 590, 720, Align.center, false);
            font.setColor(Color.WHITE);
            combatDoneButton.draw(batch);
        }
        font.setColor(Color.WHITE);
    }

    private void updateCombat() {
        if (!combat.isOver()) {
            for (int i = 0; i < abilityButtons.length; i++) {
                Ability ability = combat.getAbilities().get(i);
                int cooldown = combat.getCooldownLeft(i);
                abilityButtons[i].setText(cooldown > 0 ? ability.getName() + " (" + cooldown + ")"
                    : ability.getName());
                abilityButtons[i].setDisabled(cooldown > 0);
                if (abilityButtons[i].update(viewport)) {
                    combat.useAbility(i);
                }
            }
        } else if (combatDoneButton.update(viewport)) {
            inCombat = false;
            if (!combat.playerWon()) {
                inGame = false;               // you lost: back to the title screen
            }
        }

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.12f, 0.04f, 0.03f, 1f);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        // ---------- LOGIC ----------
        if (characterCreation) {
            updateCharacterCreation();
        } else if (characterOverview){
            if (beginAdventureButton.update(viewport)) {
                characterOverview = false;
                inGame = true;
            }
        } else if (!inGame) {
            if (newGameButton.update(viewport)) {
                characterCreation = true;
                selectedClass = new Knight();
                selectedGender = Gender.MALE;
                playerName = NameGenerator.randomName(selectedGender);
                typingName = false;
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
        } else if (inCombat){
            updateCombat();
        }
        else { // game screen
            if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
                startCombat(new Wolf());
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                paused = !paused;
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

        // ---------- DRAWING ----------
        batch.begin();
        if (characterCreation) {
            batch.draw(gameBackground, 0, 0, 1920, 1080);
            drawCharacterCreation();
        } else if (characterOverview){
            drawCharacterOverview();
        }else if (!inGame) {
            batch.draw(image, 0, 0, 1920, 1080);
            newGameButton.draw(batch);
            loadGameButton.draw(batch);
            settingsButton.draw(batch);
            quitButton.draw(batch);
        } else if (inCombat) {
            drawCombat();
        } else {
            // Normal gameplay screen
            batch.draw(gameBackground, 0, 0, 1920, 1080);
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
        if (portrait != null) {
            portrait.dispose();
        }
        if (enemyTexture != null) {
            enemyTexture.dispose();
        }
        if (pixel != null) {
            pixel.dispose();
        }
    }
}

