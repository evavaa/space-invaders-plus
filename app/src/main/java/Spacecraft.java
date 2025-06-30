// SpaceShip.java
// Used for SpaceInvader

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.util.List;

public class Spacecraft extends Actor implements GGKeyListener
{
    private int numShots = 0;
    private SpaceInvader spaceInvader;
    private boolean isAutoTesting = false;
    private List<String> controls = null;
    private int controlIndex = 0;
    private static final String IMAGE = "sprites/spaceship.gif";
    public Spacecraft(SpaceInvader spaceInvader)
    {
        super(IMAGE);
        this.spaceInvader = spaceInvader;
    }

    public void setTestingConditions(boolean isAutoTesting, List<String> controls) {
        this.isAutoTesting = isAutoTesting;
        this.controls = controls;
    }

    private void autoMove() {
        if (isAutoTesting) {
            if (controls != null && controlIndex < controls.size()) {
                String control = controls.get(controlIndex);
                Location next = null;

                switch(control) {
                    case "L":
                        next = getLocation().getAdjacentLocation(Location.WEST);
                        moveTo(next);
                        break;

                    case "R":
                        next = getLocation().getAdjacentLocation(Location.EAST);
                        moveTo(next);
                        break;

                    case "F":
                        Bomb bomb = new Bomb();
                        gameGrid.addActor(bomb, getLocation());
                        numShots++;
                        break;
                    case "E":
                        spaceInvader.setIsGameOver(true);
                        break;
                }
                controlIndex++;
            }
        }
    }

    public void act()
    {
        autoMove();
    }

    public boolean keyPressed(KeyEvent keyEvent)
    {
        Location next = null;
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_LEFT:
                next = getLocation().getAdjacentLocation(Location.WEST);
                moveTo(next);
                break;

            case KeyEvent.VK_RIGHT:
                next = getLocation().getAdjacentLocation(Location.EAST);
                moveTo(next);
                break;

            case KeyEvent.VK_SPACE:
                Bomb bomb = new Bomb();
                gameGrid.addActor(bomb, getLocation());
                numShots++;
                break;
        }

        return false;
    }

    private void moveTo(Location location)
    {
        if (location.x > 10 && location.x < 190)
            setLocation(location);
    }

    /* Getter method for obtaining the number of shots fired */
    public int getNumShots() {
        return numShots;
    }

    @Override
    public boolean keyReleased(KeyEvent keyEvent) {
        return false;
    }
}
