public class AlienPowerful extends Alien {
    private static final String IMAGE_FILE = "sprites/powerful_alien.gif";
    private static final int MAX_NUM_LIFE = 5;
    private static final String TYPE = "powerful";

    public AlienPowerful(int row, int col) {
        super(IMAGE_FILE, row, col, MAX_NUM_LIFE, TYPE);
    }



}
