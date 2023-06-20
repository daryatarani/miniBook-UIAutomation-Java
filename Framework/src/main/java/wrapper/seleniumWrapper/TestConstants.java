package wrapper.seleniumWrapper;

import java.io.File;

public class TestConstants
{
    public static class ConfigTypesKey
    {
        public static final String BROWSER = "Browser";
        public static final String IMPLICIT_WAIT_TIMEOUT = "ImplicitWaitTimeout";
        public static final String OBJECT_IDENTIFICATION_TIMEOUT = "ObjectIdentificationTimeOut";
        public static final String PROTOCOL = "Protocol";
        public static final String ENV = "Env";
    }

    public static class AccessibilityTest
    {
        public static final String REPORT_FOLDER = "AccessibilityTest";
        public static final String HTML_CS_REPORT_FOLDER = "HtmlCs_Report";
        public static final String AXE_REPORT_FOLDER = "Axe_Report";
    }

    public static class PathVariables
    {
        public static final File BaseDirectory = new File(new File(System.getProperty("user.dir")).getParent());
        public static final String BASE_DIRECTORY_PATH = BaseDirectory.getPath();
        public static final String FRAMEWORK_DIRECTORY = BASE_DIRECTORY_PATH + File.separator + "Framework";
        public static final String GET_BASE_DIRECTORY = System.getProperty("user.dir");
        public static final String HTML_REPORT_FOLDER = BASE_DIRECTORY_PATH + File.separator +"Reports";
        public static final String EXTENT_REPORT_NAME = "ExtentReport";
        public static final String CONFIG_FILE_NAME = "configSettings.properties";
        @SuppressWarnings("unused")
        public static final String LOGGER_PROPERTIES_FILE_NAME = "Log4j2.properties";
        @SuppressWarnings("unused")
        public static final String LOG_NAME = File.separator +"Log";
        public static final String EXTENT_CONFIG_FILE_PATH = String.join(File.separator, "src", "main", "resources");
        public static final String EXTENT_CONFIG_XML = "ExtentConfig.xml";
        public static final String EXECUTION_REPORT_PATH = "ExecutionReportPath";
        public static final String ACC_TESTING_HTMLCS_REPORT_PATH = String.join(File.separator,GET_BASE_DIRECTORY, "target","java-a11y","htmlcs","html");
        public static final String ACC_TESTING_AXE_REPORT_PATH = String.join(File.separator,GET_BASE_DIRECTORY, "target","java-a11y","axe","html");
        public static final String TARGET_FILE_PATH = String.join(File.separator,  TestConstants.PathVariables.GET_BASE_DIRECTORY, "target");
        public static final String CUCUMBER_TEST_RESULT_JSON_FILE = "cucumber.json";
        public static final String CUCUMBER_TEST_RESULT_JSON_FILE_PATH = String.join(File.separator, TARGET_FILE_PATH, CUCUMBER_TEST_RESULT_JSON_FILE);
    }

    public enum WebDriverAction
    {
        CLEAR,
        CLICK,
        DOUBLE_CLICK,
        JAVA_SCRIPT_CLICK,
        INPUT,
        WAIT_INPUT,
        JAVA_SCRIPT_INPUT,
        SELECT,
        DESELECT,
        FOCUS,
        SCROLL_TO_TOP,
        SCROLL_TO_BOTTOM,
        JAVA_SCRIPT_SCROLL_TO_ELEMENT
    }

    public enum LocatorType
    {
        ID,
        NAME,
        CLASS_NAME,
        XPATH,
        TAG_NAME,
        CSS_SELECTOR,
        LINK_TEXT,
        PARTIAL_LINK_TEXT,
    }

    public enum WebAttributes
    {
        VALUE("value"),
        CHECKED("checked");

        private final String name;

        WebAttributes(String s)
        {
            name = s;
        }
        @SuppressWarnings("unused")
        public boolean equalsName(String otherName) {
            return name.equals(otherName);
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public static final String KEY_VALUE = "9642F816BECDC1144DFF1DE9A7569";
    public static final String ALGO_AES = "AES";
    public static final String ALGO_AES_ECB_PKCS = "AES/ECB/PKCS5PADDING";
    public static final String ALGO_SHA1 = "SHA-1";

    public enum SeleniumExceptionType
    {
        StaleElementReferenceException,
        ElementClickInterceptedException,
        ElementNotInteractableException,
        InvalidElementStateException,
        UnhandledAlertException,
    }
}
