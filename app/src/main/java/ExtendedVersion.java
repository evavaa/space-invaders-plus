import ch.aplu.jgamegrid.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.awt.Point;

public class ExtendedVersion extends Version {

    private ArrayList<Point> powerfulAlienLocations = new ArrayList<>();
    private ArrayList<Point> invulnerableAlienLocations = new ArrayList<>();
    private ArrayList<Point> multipleAlienLocations = new ArrayList<>();
    private boolean hasAccelerated = false;

    public ExtendedVersion(SpaceInvader spaceInvader) {
        super(spaceInvader);
    }

    private ArrayList<Point> convertFromProperty(Properties prop, String propertyName) {
        String positionString = prop.getProperty(propertyName);
        ArrayList<Point> alienLocations = new ArrayList<>();
        if (positionString != null) {
            String[] locations = positionString.split(";");
            for (String location : locations) {
                String[] locationPair = location.split("-");
                int rowIndex = Integer.parseInt(locationPair[0]);
                int colIndex = Integer.parseInt(locationPair[1]);
                alienLocations.add(new Point(rowIndex, colIndex));
            }
        }
        return alienLocations;
    }
    private void setupAlienLocations() {
        powerfulAlienLocations = convertFromProperty(spaceInvader.PROPERTIES, "Powerful.locations");
        invulnerableAlienLocations = convertFromProperty(spaceInvader.PROPERTIES,"Invulnerable.locations");
        multipleAlienLocations = convertFromProperty(spaceInvader.PROPERTIES,"Multiple.locations");
    }

    private boolean arrayContains(ArrayList<Point> locations, int rowIndex, int colIndex) {
        for (Point location : locations) {
            if (location.getX() == rowIndex && location.getY() == colIndex) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Alien[][] setupAliens(Alien[][] alienGrid) {
        setupAlienLocations();
        boolean isAutoTesting = Boolean.parseBoolean(spaceInvader.PROPERTIES.getProperty("isAuto"));
        String aliensControl = spaceInvader.PROPERTIES.getProperty("aliens.control");
        List<String> movements = null;
        if (aliensControl != null) {
            movements = Arrays.asList(aliensControl.split(";"));
        }
        for (int i = 0; i < alienGrid.length; i++) {
            for (int j = 0; j < alienGrid[0].length; j++) {
                Alien alien;
                if (arrayContains(powerfulAlienLocations, i, j)) {
                    alien = new AlienPowerful(i, j);
                } else if (arrayContains(invulnerableAlienLocations, i, j)) {
                    alien = new AlienInvulnerable(i, j);
                } else if (arrayContains(multipleAlienLocations, i, j)) {
                    alien = new AlienMultiple(i, j);
                } else {
                    alien = new AlienNormal(i, j);
                }
                getSpaceInvader().addActor(alien, new Location(100 - 5 * alienGrid[0].length + 10 * j, 10 + 10 * i));
                alien.setTestingConditions(isAutoTesting, movements);
                alienGrid[i][j] = alien;
            }
        }
        return alienGrid;
    }

    @Override
    public void update() {
        super.update();

        // Randomly multiply for multiple alien
        ArrayList<Actor> multipleAliens = getSpaceInvader().getActors(AlienMultiple.class);
        for (Actor alien : multipleAliens) {
            ((AlienMultiple)alien).randomlyMultiply();
        }

        // Adjust movement speed of aliens
        if (moveFast() && !hasAccelerated) {
            notifyMoveFast();
            hasAccelerated = true;
        } else if (! moveFast()) {
            hasAccelerated = false;
        }

    }

    private void notifyMoveFast() {
        logResult.append("Aliens start moving fast");
        logResult.append("\n");
    }

    public boolean moveFast() {
        Spacecraft spacecraft = (Spacecraft) getSpaceInvader().getActors(Spacecraft.class).get(0);
        if (spacecraft.getNumShots() == 10) {
            Alien.setSpeed(2);
            return true;
        }
        if (spacecraft.getNumShots() == 50) {
            Alien.setSpeed(3);
            return true;
        }
        if (spacecraft.getNumShots() == 100) {
            Alien.setSpeed(4);
            return true;
        }
        if (spacecraft.getNumShots() == 500) {
            Alien.setSpeed(5);
            return true;
        }
        return false;
    }


}
