import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.Random;

public class AlienMultiple extends Alien {
    private static final String IMAGE_FILE = "sprites/multiple_alien.gif";
    private static final int PROB_MULTIPLY = 20;
    private static final int INITIAL_ARRAY_Y = 10;
    private static final String TYPE = "multiple";
    private boolean isMultiplied = false;

    public AlienMultiple(int row, int col) {
        super(IMAGE_FILE, row, col, TYPE);
    }

    private boolean topRowEmpty() {
        ArrayList<Actor> actors = gameGrid.getActors(Alien.class);
        for (Actor alien : actors) {
            if (alien.getY() <= INITIAL_ARRAY_Y) {
                return false;
            }
        }
        return true;
    }
    public void randomlyMultiply() {
        if (isMultiplied) {
            return;
        }
        Random rand = new Random();
        int outcome = rand.nextInt(100);
        if (outcome <= PROB_MULTIPLY) {
            if (topRowEmpty()) {
                multiply();
            }
        }
    }

    private void multiply() {
        isMultiplied = true;

        // update row index of all aliens on the game grid
        alienRowIncrement();
        Alien[][] newAlienGrid = insertTopRow();

        // fill the top row with normal alien
        int numCols = ((SpaceInvader)gameGrid).getAlienNumCol();
        for (int j = 0; j < numCols; j++) {
            Alien newAlien = new AlienNormal(0, j);
            newAlien.setNumSteps(this.getNumSteps());
            gameGrid.addActor(newAlien, new Location(this.getX() - (this.getColIndex() - j) * ALIEN_POS_OFFSET,
                    this.getY() - this.getRowIndex() * ALIEN_POS_OFFSET), this.getDirection());
            newAlien.setTestingConditions(super.getIsAutoTesting(), super.getMovements());
            newAlienGrid[0][j] = newAlien;
        }
        /* Update on the alien grid in space invader */
        ((SpaceInvader)gameGrid).setAlienGrid(newAlienGrid);

        /* Synchronize all the alien */
        gameGrid.actAll();
        for (Actor actor : gameGrid.getActors(Alien.class)) {
            actor.setSlowDown(ACT_SLOW_DOWN_FACTOR);
        }
    }

    /**
     * Increment the row index of all aliens by one
     */
    private void alienRowIncrement() {
        ArrayList<Actor> actors = gameGrid.getActors(Alien.class);
        for (Actor actor : actors) {
            Alien alien = (Alien)actor;
            (alien).setArrayRowIndex(alien.getRowIndex() + 1);
        }
    }

    private Alien[][] insertTopRow() {
        int numRols = ((SpaceInvader)gameGrid).getAlienNumRow()+1;
        int numCols = ((SpaceInvader)gameGrid).getAlienNumCol();
        Alien[][] alienGrid = ((SpaceInvader)gameGrid).getAlienGrid();
        Alien[][] newAlienGrid = new Alien[numRols][numCols];

        for (int i=1; i<numRols; i++) {
            for (int j=0; j<numCols; j++) {
                newAlienGrid[i][j] = alienGrid[i-1][j];
            }
        }
        return newAlienGrid;
    }
}
