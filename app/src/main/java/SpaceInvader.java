// SpaceInvader.java
// Sprite images from http://www.cokeandcode.com/tutorials
// Nice example how the different actor classes: SpaceShip, Bomb, SpaceInvader, Explosion
// act almost independently of each other. This decoupling simplifies the logic of the application

import ch.aplu.jgamegrid.*;

import java.awt.Point;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SpaceInvader extends GameGrid implements GGKeyListener {
    private static final int WINDOW_WIDTH = 200;
    private static final int WINDOW_HEIGHT = 100;
    private static final int CELL_SIZE = 5;
    private static final boolean IS_NAVIGATION = false;
    private static final String INSTRUCTION_1 = "Use <- -> to move, spacebar to shoot";
    private static final String INSTRUCTION_2 = "Press any key to start...";
    private static final String WIN_IMAGE = "sprites/you_win.gif";
    private static final String LOST_IMAGE = "sprites/explosion2.gif";
    private static final String CREDIT_MSG = "Game constructed with JGameGrid (www.aplu.ch)";
    private static final String SHOTS_SUMMARY = "Number of shots: ";
    private static final Font FONT = new Font("SansSerif", Font.PLAIN, 12);

    private boolean isGameOver = false;
    public final Properties PROPERTIES;
    private boolean isAutoTesting;
    private static int alienNumRow;
    private static int alienNumCol;
    private String spaceShipControl;
    private Spacecraft spacecraft = null;
    private Alien[][] alienGrid = null;
    private Version version;

    public SpaceInvader(Properties properties) {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, CELL_SIZE, IS_NAVIGATION);
        this.PROPERTIES = properties;
    }

    public String runApp(boolean isDisplayingUI) {
        setSimulationPeriod(Integer.parseInt(PROPERTIES.getProperty("simulationPeriod")));
        alienNumRow = Integer.parseInt(PROPERTIES.getProperty("rows"));
        alienNumCol = Integer.parseInt(PROPERTIES.getProperty("cols"));
        isAutoTesting = Boolean.parseBoolean(PROPERTIES.getProperty("isAuto"));

        // Display instruction on screen
        getBg().setFont(FONT);
        getBg().drawText(INSTRUCTION_1, new Point(400, 330));
        getBg().drawText(INSTRUCTION_2, new Point(400, 350));

        // Draw alien and spacecraft on game grid
        setupVersion();
        alienGrid = new Alien[alienNumRow][alienNumCol];
        alienGrid = version.setupAliens(alienGrid);
        setupSpacecraft();
        addKeyListener(this);

        if (isDisplayingUI) {
            show();
        }

        if (isAutoTesting) {
            setBgColor(java.awt.Color.black); // Erase text
            doRun();
        }

        while (!isGameOver) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        doPause();
        return version.logResult.toString();
    }

    private void setupVersion() {
        if (PROPERTIES.getProperty("version").equals("simple")) {
            version = new SimpleVersion(this);
        } else if (PROPERTIES.getProperty("version").equals("plus")) {
            version = new ExtendedVersion(this);
        }
    }

    private void setupSpacecraft() {
        spacecraft = new Spacecraft(this);
        addActor(spacecraft, new Location(100, 90));

        List<String> controls = null;
        if (spaceShipControl != null) {
            controls = Arrays.asList(spaceShipControl.split(";"));
        }
        spacecraft.setTestingConditions(isAutoTesting, controls);
        addKeyListener(spacecraft);
    }

    @Override
    public void act() {
        if (!isGameOver) {
            version.update();
        }

        // if game is still going and all aliens are removed, player win the game
        if (!isGameOver && (getNumberOfActors(Alien.class) == 0)) {
            removeKeyListener(spacecraft);
            renderWinScreen();
            isGameOver = true;
            return;
        }
        // check collision between alive aliens and bombs/spacecraft
        ArrayList<Actor> aliens = getActors(Alien.class);
        for (Actor actor : aliens) {
            Alien alien = (Alien) actor;
            if (!alien.isRemoved()) {
                // if any alien hit the spacecraft, game over
                if (alien.hitSpacecraft()) {
                    renderFailScreen();
                    isGameOver = true;
                    return;
                }
                if (alien.hitBomb()) {
                    version.notifyAlienHit(alien);
                }
            }
        }
    }

    private void renderFailScreen() {
        removeAllActors();
        addActor(new Actor(LOST_IMAGE), spacecraft.getLocation());
    }

    private void renderWinScreen() {
        removeAllActors();
        getBg().drawText(SHOTS_SUMMARY + spacecraft.getNumShots(), new Point(10, 30));
        getBg().drawText(CREDIT_MSG, new Point(10, 50));
        addActor(new Actor(WIN_IMAGE), new Location(100, 60));
    }

    public void setIsGameOver(boolean isOver) {
        isGameOver = isOver;
    }

    public boolean keyPressed(KeyEvent evt) {
        if (!isRunning()) {
            setBgColor(java.awt.Color.black); // Erase text
            doRun();
        }
        return false; // Do not consume key
    }

    public boolean keyReleased(KeyEvent evt) {
        return false;
    }

    public int getAlienNumCol() {
        return alienNumCol;
    }

    public int getAlienNumRow() {
        return alienNumRow;
    }

    public Alien[][] getAlienGrid() {
        return alienGrid;
    }

    public void setAlienGrid(Alien[][] alienGrid) {
        this.alienGrid = alienGrid;
    }
}
