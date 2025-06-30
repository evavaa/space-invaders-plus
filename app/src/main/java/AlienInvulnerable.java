import ch.aplu.jgamegrid.Actor;

import java.util.List;
import java.util.Random;

public class AlienInvulnerable extends Alien {
    private static final String IMAGE_FILE = "sprites/invulnerable_alien.gif";
    private boolean isTransparent = false;
    private static final int TRANS_DURATION = 4;
    private int currTransDuration = 0;
    private static final int PROB_TRANSPARENT = 30;
    private static final String TYPE = "invulnerable";

    public AlienInvulnerable(int row, int col) {
        super(IMAGE_FILE, row, col, TYPE);
    }

    @Override
    public void act() {
        super.act();
        if (isTransparent) {
            if (currTransDuration == TRANS_DURATION) {
                isTransparent = false;
                currTransDuration = 0;
            } else {
                currTransDuration++;
            }
        } else {
            randomlyToTransparent();
        }
    }

    public void randomlyToTransparent() {
        Random rand = new Random();
        int outcome = rand.nextInt(100);
        if (outcome <= PROB_TRANSPARENT) {
            isTransparent = true;
        }
    }

    @Override
    public boolean hitBomb() {
        if (isTransparent) {
            return false;
        }
        return super.hitBomb();
    }

}
