// Bomb.java
// Used for SpaceInvader

import ch.aplu.jgamegrid.*;

import java.util.List;

public class Bomb extends Actor
{
    private static final String BOMB_IMAGE = "sprites/bomb.gif";

    public Bomb()
    {
        super(BOMB_IMAGE);
    }

    public void reset()
    {
        setDirection(Location.NORTH);
    }

    public void act()
    {
        // Acts independently searching a possible target and bring it to explosion
        move();
        if (getLocation().y < 5)
            removeSelf();
    }
}
