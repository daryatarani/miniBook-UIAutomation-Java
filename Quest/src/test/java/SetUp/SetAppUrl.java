package SetUp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wrapper.seleniumWrapper.ConfigHelper;
import static ApplicationConstants.TestConstants.*;
import static ApplicationConstants.TestConstants.PathVariables.*;

public class SetAppUrl
{
    public static Logger Log = LogManager.getLogger(SetAppUrl.class.getName());

    public static String SetUrl(String protocol)
    {
        var url = ConfigHelper.getConfigValue(ConfigHelper.getConfigValue(ENV, ConfigFilePath) + LOGIN_URL, ConfigFilePath);
        protocol = switch(protocol.toLowerCase())
        {
            case "http" -> "http://";
            case "https" -> "https://";
            default ->  protocol;
        };
        Log.info("The Application URl for the Quest is set as " + protocol + url);
        return protocol + url;
    }
}