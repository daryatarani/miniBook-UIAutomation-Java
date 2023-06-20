package ApplicationConstants;

import java.io.File;
import static wrapper.seleniumWrapper.TestConstants.PathVariables.*;

public class TestConstants {

    public static final String CONFIG_FILE_NAME = "UIConfig.properties";
    public static final String LOGIN_URL = "LoginURL";

    public static class PathVariables {

        public static final String ConfigFilePath = String.join(File.separator, BASE_DIRECTORY_PATH,
                "Quest", "src", "test", "resources", CONFIG_FILE_NAME);
    }

    public static class Selectors {
        //MiniBook
        public static final String Elements = "Elements";
        public static final String TextBox = "//div[@class='main-header' and @id='app']//li[1]";
        public static final String CheckBox = "//div[@class='main-header' and @id='app']//li[2]";
        public static final String RadioButton = "//div[@class='main-header' and @id='app']//li[3]";
        public static final String WebTables = "//div[@class='main-header' and @id='app']//li[4]";
        public static final String Buttons = "//div[@class='main-header' and @id='app']//li[5]";
        public static final String Links = "//div[@class='main-header' and @id='app']//li[6]";
        public static final String BrokenLinks = "//div[@class='main-header' and @id='app']//li[7]";
        public static final String UploadsAndDownloads = "//div[@class='main-header' and @id='app']//li[8]";
        public static final String DynamicProperties = "//div[@class='main-header' and @id='app']//li[9]";
        public static final String RadioButtonText="//p[@id='output']";
        public static final String DoubleClickMe="[id='doubleClickBtn']";
        public static final String DoubleClickText="[id='doubleClickMessage']";
        //Alerts
        public static final String AlertsAndWindows = "Alerts, Frame &amp; Windows";
        public static final String Alerts = "//div[@class='element-list collapse show']//li[2]";
        public static final String ClickMeToSeeAlert="[id='alertButton']";
    }
    public static final String YES = "Yes";
    public static final String NO = "No";
}