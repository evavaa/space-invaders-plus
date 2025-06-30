import ch.aplu.jgamegrid.Location;

import java.util.Arrays;
import java.util.List;

public class SimpleVersion extends Version {

    public SimpleVersion(SpaceInvader spaceInvader) {
        super(spaceInvader);
    }

    @Override
    public Alien[][] setupAliens(Alien[][] alienGrid) {
        boolean isAutoTesting = Boolean.parseBoolean(spaceInvader.PROPERTIES.getProperty("isAuto"));
        String aliensControl = spaceInvader.PROPERTIES.getProperty("aliens.control");
        List<String> movements = null;
        if (aliensControl != null) {
            movements = Arrays.asList(aliensControl.split(";"));
        }

        for (int i = 0; i < alienGrid.length; i++) {
            for (int j = 0; j < alienGrid[0].length; j++) {
                Alien alien = new AlienNormal(i, j);
                getSpaceInvader().addActor(alien, new Location(100 - 5 * alienGrid[0].length + 10 * j, 10 + 10 * i));
                alien.setTestingConditions(isAutoTesting, movements);
                alienGrid[i][j] = alien;
            }
        }
        return alienGrid;
    }

}
