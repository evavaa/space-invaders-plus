import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class PropertiesLoader {
    public static Properties loadPropertiesFile(String propertiesFile) {
        try (InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream(propertiesFile)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // remove properties with no value
            Set<Object> keys = prop.keySet();
            for (Object key : keys) {
                if (prop.getProperty(key.toString()).equals("")) {
                    prop.remove(key.toString());
                }
            }
            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
