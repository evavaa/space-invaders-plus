public class AlienNormal extends Alien {
    private static final String IMAGE_FILE = "sprites/alien.gif";
    private static final String TYPE = "alien";

    public AlienNormal(int row, int col) {
        super(IMAGE_FILE, row, col, TYPE);
    }
}
