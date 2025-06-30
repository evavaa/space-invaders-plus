public abstract class Version {
    protected SpaceInvader spaceInvader;
    protected StringBuilder logResult = new StringBuilder();

    public Version(SpaceInvader spaceInvader) {
        this.spaceInvader = spaceInvader;
    }

    public SpaceInvader getSpaceInvader() {
        return spaceInvader;
    }

    public abstract Alien[][] setupAliens(Alien[][] alienGrid);

    public void update() {
        notifyAlienPosition();
    }

    public void notifyAlienPosition() {
        logResult.append("Alien locations: ");
        for (int i = 0; i < spaceInvader.getAlienNumRow(); i++) {
            for (int j = 0; j < spaceInvader.getAlienNumCol(); j++) {
                Alien alienData = spaceInvader.getAlienGrid()[i][j];

                String isDeadStatus = alienData.isRemoved() ? "Dead" : "Alive";
                String gridLocation = "0-0";
                if (!alienData.isRemoved()) {
                    gridLocation = alienData.getX() + "-" + alienData.getY();
                }
                String alienDataString = String.format("%s@%d-%d@%s@%s#", alienData.getType(),
                        alienData.getRowIndex(), alienData.getColIndex(), isDeadStatus, gridLocation);
                logResult.append(alienDataString);
            }
        }
        logResult.append("\n");
    }

    public void notifyAlienHit(Alien alien) {
        String alienData = String.format("%s@%d-%d", alien.getType(), alien.getRowIndex(), alien.getColIndex());
        logResult.append("An alien has been hit.");
        logResult.append(alienData + "\n");
    }
}
