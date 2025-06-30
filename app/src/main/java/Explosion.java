import ch.aplu.jgamegrid.Actor;

public class Explosion extends Actor {
    /* Explosion image */
    private static final String EXPLOSION_IMAGE_FILE = "sprites/explosion1.gif";
    private static final int ACT_SLOW_DOWN_FACTOR = 3;
    public Explosion() {
        super(EXPLOSION_IMAGE_FILE);
        setSlowDown(ACT_SLOW_DOWN_FACTOR);
    }

    public void act() {
        removeSelf();
    }
}