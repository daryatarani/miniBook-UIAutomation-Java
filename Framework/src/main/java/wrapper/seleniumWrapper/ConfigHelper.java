package wrapper.seleniumWrapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;

public class ConfigHelper
{
    protected static final Properties properties = new Properties();
    private static final Logger Log = LogManager.getLogger(ConfigHelper.class.getName());

    private static final String FILE_PATH = TestConstants.PathVariables.FRAMEWORK_DIRECTORY
            + File.separator + TestConstants.PathVariables.CONFIG_FILE_NAME;

    public static synchronized String getConfigValue(String keyName)
    {
        try(var inputStream = new FileInputStream(FILE_PATH))
        {

            properties.load(inputStream);
            var keyValue = properties.getProperty(keyName);
            Log.debug("The value of %s is fetched as : %s".formatted(keyName, keyValue));
            return keyValue;
        }
        catch (Exception ex)
        {
            Log.error("The properties file couldn't be loaded due to : %s".formatted(ex.getMessage()));
            return null;
        }
    }

    public static synchronized String getConfigValue(String keyName, String filePath)
    {
        try(var inputStream = new FileInputStream(filePath))
        {

            properties.load(inputStream);
            var keyValue = properties.getProperty(keyName);
            Log.debug("The value of %s is fetched as : %s".formatted(keyName, keyValue));
            return keyValue;
        }
        catch (Exception ex)
        {
            Log.error("The properties file couldn't be loaded due to : %s".formatted(ex.getMessage()));
            return null;
        }
    }

    public static synchronized void setConfigValue(String keyName, String newValue)
    {
        try(var inputStream = new FileInputStream(FILE_PATH))
        {
            properties.load(inputStream);
            properties.setProperty(keyName, newValue);
            Log.debug("The value of %s is set as : %s".formatted(keyName, properties.getProperty(keyName)));
            try(var outputStream = new FileWriter(FILE_PATH))
            {
                properties.store(outputStream,null);
            }
        }
        catch (Exception ex)
        {
            Log.error("The properties file couldn't be set due to : %s".formatted(ex.getMessage()));
        }
    }

    public static synchronized void setConfigValue(String keyName, String newValue, String filePath)
    {
        try(var inputStream = new FileInputStream(filePath))
        {
            properties.load(inputStream);
            properties.setProperty(keyName, newValue);
            Log.debug("The value of %s is set as : %s".formatted(keyName, properties.getProperty(keyName)));
            try(var outputStream = new FileWriter(filePath))
            {
                properties.store(outputStream,null);
            }
        }
        catch (Exception ex)
        {
            Log.error("The properties file couldn't be set due to : %s".formatted(ex.getMessage()));
        }
    }
}
