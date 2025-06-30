// Alien.java
// Used for SpaceInvader

import ch.aplu.jgamegrid.*;

import java.util.List;

public abstract class Alien extends Actor {
    private static int speed = 1;
    public static final int DEFAULT_DIST = 5;
    private String type;
    private int numLives = 1;
    private int arrayRowIndex;
    private int arrayColIndex;
    public static final int ACT_SLOW_DOWN_FACTOR = 7;

    /* Auto testing related attributes */
    private boolean isAutoTesting;
    private boolean isMoving = true;
    private List<String> movements;
    private int movementIndex = 0;

    /* Game grid size */
    private int numSteps = 7;
    public static final int MAX_NUM_STEP = 16;
    public static final int LARGEST_Y_VALUE = 90;

    /* Angles */
    public static final int TURN_RIGHT = 90;
    public static final int TURN_LEFT = -90;
    // The horizontal and vertical distance between each alien gif
    public static final int ALIEN_POS_OFFSET = 10;

    public Alien(String filename, int row, int col, String type) {
        super(filename);
        this.type = type;
        setSlowDown(ACT_SLOW_DOWN_FACTOR);
        arrayRowIndex = row;
        arrayColIndex = col;
    }
    public Alien(String filename, int row, int col, int numLives, String type) {
        super(filename);
        this.type = type;
        setSlowDown(ACT_SLOW_DOWN_FACTOR);
        arrayRowIndex = row;
        arrayColIndex = col;
        this.numLives = numLives;
    }

    public void reduceLife() {
        numLives--;
    }

    public boolean hitSpacecraft() {
        List<Actor> spacecraft = gameGrid.getActorsAt(getLocation(), Spacecraft.class);
        return spacecraft.size() > 0;
    }

    /**
     * Check if the alien hit bomb
     */
    public boolean hitBomb() {
        List<Actor> bombs = gameGrid.getActorsAt(getLocation(), Bomb.class);
        if (bombs.size() > 0) {
            //spaceInvader.notifyAlienHit(actors);
            gameGrid.removeActorsAt(getLocation(), Bomb.class);
            reduceLife();
            if (numLives == 0) {
                gameGrid.addActor(new Explosion(), getLocation());
                removeSelf();
            }
            return true;
        }
        return false;
    }

    private void checkMovements() {
        if (isAutoTesting) {
            if (movements != null && movementIndex < movements.size()) {
                String movement = movements.get(movementIndex);
                if (movement.equals("S")) {
                    isMoving = false;
                } else if (movement.equals("M")) {
                    isMoving = true;
                }
                movementIndex++;
            }
        }
    }
    public void act() {
        checkMovements();

        if (!isMoving || this.isRemoved()) {
            return;
        }
        if (numSteps < MAX_NUM_STEP) {
            move(Math.min(speed, MAX_NUM_STEP-numSteps) * DEFAULT_DIST);
            numSteps += speed;
        } else {
            numSteps = 0;
            int angle;
            if (getDirection() == 0) {
                angle = TURN_RIGHT;
            } else {
                angle = TURN_LEFT;
            }
            turn(angle);
            move();
            turn(angle);
        }
        if (getLocation().y > LARGEST_Y_VALUE) {
            removeSelf();
        }
    }

    public int getNumSteps() {
        return numSteps;
    }

    public void setNumSteps(int numSteps) {
        this.numSteps = numSteps;
    }

    public String getType() {
        return type;
    }

    public int getColIndex() {
        return arrayColIndex;
    }
    public int getRowIndex() {
        return arrayRowIndex;
    }

    public void setArrayRowIndex(int arrayRowIndex) {
        this.arrayRowIndex = arrayRowIndex;
    }

    public boolean getIsAutoTesting() {
        return isAutoTesting;
    }

    public List<String> getMovements() {
        return movements;
    }

    public static void setSpeed(int speed) {
        Alien.speed = speed;
    }

    public void setTestingConditions(boolean isAutoTesting, List<String> movements) {
        this.isAutoTesting = isAutoTesting;
        this.movements = movements;
    }
}
