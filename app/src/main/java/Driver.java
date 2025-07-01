import java.util.Properties;

public class Driver {
    public static final String PROPERTIES_PATH = "properties/game1.properties";

    public static void main(String[] args) {
        String propertiesPath = PROPERTIES_PATH;
        if (args.length > 0) {
            propertiesPath = args[0];
        }
        final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);

        String logResult = new SpaceInvader(properties).runApp(true);
        System.out.println("logResult = " + logResult);
    }
}
