package wrapper.utilities;

import com.fasterxml.jackson.databind.*;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import net.datafaker.Faker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.*;
import org.apache.commons.lang3.time.DateUtils;
import org.json.*;
import org.testng.Assert;
import org.testng.util.Strings;
import wrapper.apiWrapper.ApiConstants;
import wrapper.seleniumWrapper.ConfigHelper;
import wrapper.seleniumWrapper.TestConstants;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import static wrapper.apiWrapper.ApiConstants.Header.*;

public class CommonMethods {

    private static final Random rand = new Random();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Faker faker = new Faker(Locale.forLanguageTag("en-AU"));

    @SuppressWarnings("unused")
    @SneakyThrows
    public static void WaitInSeconds(int seconds) {
        var now = System.currentTimeMillis();
        var timeToWait = System.currentTimeMillis() + seconds * 1000L;
        while(now < timeToWait){
            now = System.currentTimeMillis();
        }
    }

    public static String GenerateRandomString(int lengthOfStringToGenerate) {
        return RandomStringUtils.random(lengthOfStringToGenerate, true, false);
    }

    public static String FirstCharToUpper(String input) {
        input = input.toLowerCase();
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String ReturnDeviceResolution(TestConstants.MobileDevice deviceName) {
        return switch (deviceName) {
            case IPHONE_12_PRO -> TestConstants.ScreenSize.IPHONE12;
            case SAMSUNG_GALAXY_S20_ULTRA -> TestConstants.ScreenSize.GALAXY_S20_ULTRA;
            case IPAD_PRO -> TestConstants.ScreenSize.IPAD_PRO;
        };
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static Map<String, String> ToMap(String jsonString) {
        return mapper.readValue(jsonString, HashMap.class);
    }

    @SuppressWarnings("unused")
    public static Object ToObject(Map<String, String> map) {
        return mapper.convertValue(map, Map.class);
    }

    @SneakyThrows
    public static JsonNode ToJsonNode(String jsonString) {
        return mapper.readTree(jsonString);
    }

    public static Map<String, String> AddDefaultHeaders() {
        var headerMap = new HashMap<String, String>();
        headerMap.put(CONTENT_TYPE, ApiConstants.ContentType.JSON);
        return headerMap;
    }

    public static Map<String, String> AddXmlHeaders() {
        var headerMap = new HashMap<String, String>();
        headerMap.put(CONTENT_TYPE, ApiConstants.ContentType.XML);
        return headerMap;
    }

    public static Map<String, String> AddMultipartFormDataHeaders() {
        var headerMap = new HashMap<String, String>();
        headerMap.put(CONTENT_TYPE, ApiConstants.ContentType.MULTIPART_FORM_DATA);
        return headerMap;
    }

    public static String GetFakeRandomDetails(String property) {
        return switch (EnumUtils.getEnumIgnoreCase(TestConstants.FakeProperty.class, property)) {
            case ADDRESS -> getRandomAddressLine();
            case CITYSUBURB -> getRandomCitySuburb();
            case STATEPROVINCE -> getRandomStateProvince();
            case POSTCODE -> getRandomPostZipCode();
            case SALUTATION -> getRandomSalutation();
            case VEHICLEIDENTIFICATIONNUMBER -> getRandomVehicleIdentificationNumber();
            case FIRSTNAME, BANKINSTITUTION, ACCOUNTNAME -> getRandomFirstName();
            case MIDDLENAME -> getRandomMiddleNameAbbr();
            case SURNAME -> getRandomSurname();
            case DATEOFBIRTH -> getRandomDate();
            case ADDRESSTYPE -> getRandomAddressType();
            case BRAND, RANDOMBRAND -> getRandomBrand();
            case VEHICLEENGINENUMBER -> getRandomVehicleEngineNumber();
            case VEHICLEREGISTRATIONNUMBER -> getRandomVehicleRegistrationNumber();
            case VEHICLEEXTERNALCOLOUR -> getRandomVehicleExternalColour();
            case RANDOMCOMPANY -> getRandomCompany();
            case RANDOMNUMBER -> getRandomNumber();
            case APPLICATIONID, CUSTOMERIDQUANTUM -> getRandomApplicationId();
            case ACCOUNTNUMBER -> getRandomAccountNumber();
            case BSBNUMBER -> getRandomBSBNumber();
            case SECURITYLIFECYCLESTATUS -> getRandomSecurityLifeCycleStatus();
            case SECURITYTYPE -> getRandomSecurityType();
            case PAYMENTMETHOD -> getRandomPaymentMethod();
            case INDPHONETYPE, TELEPHONENUMBERTYPE -> getRandomIndPhoneType();
            case ORGPHONETYPE -> getRandomOrgPhoneType();
            case MOBILENUMBER -> getRandomMobileNumber();
            case PHONENUMBER, TELEPHONENUMBER -> getRandomPhoneNumber();
            case QUESTINDADDRESSTYPE ->  getRandomQuestIndAddressType();
            case QUESTORGADDRESSTYPE -> getRandomQuestOrgAddressType();
            case ABN -> getRandomAbn();
            case EMAIL -> getRandomEmail();
            case EMAILTYPE -> getRandomEmailType();
            case PRIMARYEMAIL, PRIMARYTELEPHONENUMBER -> String.valueOf(getRandomBooleanValue());
            case CUSTOMERCORRESPONDENCEPREFERENCE -> getRandomCorrespondenceType();
            case CUSTOMERMARKETINGPREFERENCE -> getRandomYN();
            case CUSTOMERID -> getRandomCustomerId();
            case ASSETIDENTIFIERS -> GenerateAlphaNumericString(13);
            default -> getUniqueId();
        };
    }

    public static String ModifyJsonPayload(String payloadString, String jsonPath, Object newValue) {
        var parsedDataContext = JsonPath.parse(payloadString);
        parsedDataContext.set(jsonPath, newValue);
        return parsedDataContext.jsonString();
    }

    public static String ModifyJsonToXmlString(String jsonPayloadString)
    {
        var json = new JSONObject(jsonPayloadString);
        return XML.toString(json);
    }

    public static String ModifyXmlToJsonString(String xmlPayloadString)    {
        var parsedDataContext = XML.toJSONObject(xmlPayloadString);
        return parsedDataContext.toString();
    }

    public static String GenerateTimeStamp() {
        return Long.toString(new Timestamp(System.currentTimeMillis()).getTime());
    }

    /**
     * Function to Generate & Attach Accessibility Testing Html Report to Execution Report Folder
     */
    @SneakyThrows
    public static synchronized void attachAccessibilityTestReport(){
        var htmlCsTargetReportDirectory = new File(TestConstants.PathVariables.ACC_TESTING_HTMLCS_REPORT_PATH);
        var axeTargetReportDirectory = new File(TestConstants.PathVariables.ACC_TESTING_AXE_REPORT_PATH);
        if (htmlCsTargetReportDirectory.exists() && Objects.requireNonNull(htmlCsTargetReportDirectory.list()).length>0
                && axeTargetReportDirectory.exists() && Objects.requireNonNull(axeTargetReportDirectory.list()).length>0){
            var htmlCsExecutionReportPath = String.join(File.separator, ConfigHelper.getConfigValue(TestConstants.PathVariables.EXECUTION_REPORT_PATH), TestConstants.AccessibilityTest.REPORT_FOLDER, TestConstants.AccessibilityTest.HTML_CS_REPORT_FOLDER);
            var axeExecutionReportPath = String.join(File.separator, ConfigHelper.getConfigValue(TestConstants.PathVariables.EXECUTION_REPORT_PATH), TestConstants.AccessibilityTest.REPORT_FOLDER, TestConstants.AccessibilityTest.AXE_REPORT_FOLDER);
            var htmlCsExecutionReportDirectory = new File(htmlCsExecutionReportPath);
            var axeExecutionReportDirectory = new File(axeExecutionReportPath);
            if (!htmlCsExecutionReportDirectory.exists()){ Assert.assertTrue(htmlCsExecutionReportDirectory.mkdirs()); }
            if (!axeExecutionReportDirectory.exists()){ Assert.assertTrue(axeExecutionReportDirectory.mkdirs()); }
            var htmlCsReportFiles = htmlCsTargetReportDirectory.listFiles();
            var axeReportFiles = axeTargetReportDirectory.listFiles();
            for (File reportFile : Objects.requireNonNull(htmlCsReportFiles)) {
                htmlCsExecutionReportDirectory = new File(String.join(File.separator, htmlCsExecutionReportPath, reportFile.getName()));
                FileUtils.copyFile(reportFile, htmlCsExecutionReportDirectory);
            }
            for (File reportFile : Objects.requireNonNull(axeReportFiles)) {
                axeExecutionReportDirectory = new File(String.join(File.separator, axeExecutionReportPath, reportFile.getName()));
                FileUtils.copyFile(reportFile, axeExecutionReportDirectory);
            }
        }
    }

    @SuppressWarnings("unused")
    public static String GenerateAlphaNumericString(int length) {
        if (length > 1) {
            var upperLimitForDigit = 0;
            upperLimitForDigit = Math.min(length, 9);
            var isVIN = false;
            // Additional checks to make the last 4 places a digit in case the length is 17
            // (as VIN is 17 digit)
            if (length == 17) {
                isVIN = true;
                // Reducing length by 4 as we will add 4 digits at the end of alphanumeric
                // string as per the requirement
                // for VIN
                length = length - 4;
            }
            var integerLength = rand.ints(1, 1, upperLimitForDigit).findFirst().orElse(0);
            var charLength = length - integerLength;
            // Generating array of random digits
            var digits = GenerateRandomDigits(integerLength);
            var digitArray = Integer.toString(digits).toCharArray();
            // Generating array of random chars
            var chars = generateRandomChars(charLength).toUpperCase();
            var charArray = chars.toCharArray();
            // Merging charArray and digitArray
            var combinedArray = new char[digitArray.length + charArray.length];
            System.arraycopy(digitArray, 0, combinedArray, 0, digitArray.length);
            System.arraycopy(charArray, 0, combinedArray, digitArray.length, charArray.length);
            // Converting array to list because Collections.shuffle does not work on arrays
            var combinedList = new ArrayList<>();
            for (char ch : combinedArray) {
                combinedList.add(ch);
            }
            // Shuffling the contents of list
            Collections.shuffle(combinedList);
            // Converting list back to string
            var returnString = combinedList.stream().map(Object::toString).collect(Collectors.joining(""));
            // Replacing 0 to 1 if the string starts with 0
            if (returnString.startsWith("0")) {
                returnString = returnString.replaceFirst("0", "1");
            }
            // Adding 4 digits to the end of alphanumeric string if the required string is
            // for VIN
            if (isVIN) {
                returnString = returnString + GenerateRandomDigits(4);
            }
            return returnString;
        } else {
            return null;
        }
    }

    public static String RemoveNonNumericCharsFromString(String inputString){
        return inputString.replaceAll("[^\\d.]", "");
    }

    public static int GenerateRandomDigits(int length) {
        if (length > 0) {
            var tempInt = (int) Math.pow(10, length - 1d);
            return tempInt + rand.nextInt(9 * tempInt);
        } else {
            return 0;
        }
    }

    public static int GenerateRandomNumber(int min, int max){
       return rand.nextInt(max - min + 1) + min;
    }

    public static String generateRandomChars(int length) {
        if (length > 0) {
            String chars = "ABCDEFGHJKLMNPRSTUVWXYZ";
            return rand.ints(length, 0, chars.length()).mapToObj(i -> "" + chars.charAt(i))
                    .collect(Collectors.joining());
        } else {
            return "";
        }
    }

    public static String getAlfaBrandAbbreviation(String brandName) {
        if (Objects.equals(brandName, "PowerTorque")) {
            return "A";
        } else if (Objects.equals(brandName, "Hino")) {
            return "H";
        } else if (Objects.equals(brandName, "Lexus")) {
            return "L";
        } else if (Objects.equals(brandName, "Power Alliance")) {
            return "R";
        } else if (Objects.equals(brandName, "Suzuki")) {
            return "S";
        } else if (Objects.equals(brandName, "Toyota")) {
            return "T";
        } else {
            return "Z";
        }
    }

    @SneakyThrows
    public static String FormatDate(String inputFormat, String outputFormat, String value){
        var inputFormatPattern = new SimpleDateFormat(inputFormat, Locale.ENGLISH);
        var outputFormatPattern = new SimpleDateFormat(outputFormat,Locale.ENGLISH);
        return outputFormatPattern.format(inputFormatPattern.parse(value));
    }

    @SneakyThrows
    public static String GetCustomDateTime(String timeZoneString, String format, int noOffsetDays) {
        var timeZone = Strings.isNotNullAndNotEmpty(timeZoneString) ? TimeZone.getTimeZone(timeZoneString) : TimeZone.getTimeZone("Australia/Sydney");
        var dateFormat = Strings.isNotNullAndNotEmpty(format) ? new SimpleDateFormat(format) : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(DateUtils.addDays(new Date(), noOffsetDays));
    }

    @SneakyThrows
    public static Object QueryJsonNodeArrayWithKeyValue(String jsonString, String node, String keyToFilter, String valueToFilter, boolean returnAsString) {
        var jsonNode = mapper.readValue(jsonString, JsonNode.class);
        var val = node != null ? jsonNode.get(node) : jsonNode;
        for (JsonNode js : val) {
            if (js.findValue(keyToFilter).asText().equals(valueToFilter)) {
                return !returnAsString ? js : js.toPrettyString();
            }
        }
        return null;
    }

    @SneakyThrows
    public static Object QueryJsonNodeArrayWithKeyValue(String jsonString, String node, Map<Object, Object> filters, boolean returnAsString) {
        var jsonNode = mapper.readValue(jsonString, JsonNode.class);
        var val = node != null ? jsonNode.get(node) : jsonNode;
        JsonNode jsRequired;
        for (JsonNode js : val) {
            var results = new ArrayList<>();
            for (Map.Entry<Object, Object> pair : filters.entrySet()) {
                results.add(js.findValue((String) pair.getKey()).asText().equals(pair.getValue()));
            }
            jsRequired = results.contains(false) ? null : js;
            if (jsRequired != null) {
                return !returnAsString ? jsRequired : jsRequired.toPrettyString();
            }
        }
        return null;
    }

    public static Random GetRandomObject() {
        return rand;
    }

    public static String GetRandomValue(String[] obj)
    {
        var randomNumber= rand.nextInt(obj.length);
        return obj[randomNumber];
    }

    public static Boolean getRandomBooleanValue() {
        return Boolean.parseBoolean(GetRandomValue(new String[]{"true", "false"}));
    }

    private static String getRandomAddressLine() {
        return String.join(" ", String.valueOf(rand.nextInt(250)), faker.address().streetName());
    }

    private static String getRandomCitySuburb() {
        return faker.address().cityName();
    }

    private static String getRandomStateProvince() {
        return GetRandomValue(new String[]{"NSW", "VIC", "QLD", "NT", "TAS", "WA", "SA", "ACT"});
    }

    private static String getRandomPostZipCode() {
        return GetRandomValue(new String[]{"2000", "2067", "2063", "2066", "2068", "2065"});
    }

    private static String getUniqueId() {
        return UUID.randomUUID().toString();
    }

    private static String getRandomSalutation() {
        return GetRandomValue(new String[]{"Dr", "Reverend", "Professor", "Major", "Lieutenant", "Officer", "Commissioner"});
    }

    private static String getRandomFirstName() {
        return faker.name().firstName();
    }

    private static String getRandomMiddleNameAbbr() {
        return GetRandomValue(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"});
    }

    private static String getRandomSurname() {
        return faker.name().lastName();
    }

    private static String getRandomDate(){
        var formatDate = new SimpleDateFormat("yyyy-MM-dd");
        var minDay = (int) LocalDate.of(1900,1,1).toEpochDay();
        var maxDay = (int) LocalDate.of(2000,1,1).toEpochDay();
        var randomDay = minDay + rand.nextInt(maxDay - minDay);
        var randomDate = Date.from(LocalDate.ofEpochDay(randomDay).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return formatDate.format(randomDate);
    }

    private static String getRandomAddressType() {
        return GetRandomValue(new String[]{"PHYSICAL", "MAILING", "UNKNOWN"});
    }

    private static String getRandomBrand() {
        return GetRandomValue(new String[]{"PowerTorque", "Hino", "Lexus", "Power Alliance", "Suzuki", "Toyota", "Mazda"});
    }

    private static String getRandomApplicationId() {
        var characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        var result = new StringBuilder();
        for (var i = 0; i < 12; i++) {
            result.append(characters.charAt(rand.nextInt(characters.length())));
        }
        return result.toString();
    }

    private static String getRandomCompany() {
        return faker.company().name(); }

    private static String getRandomNumber() {
        return faker.number().digits(7);
    }

    private static String getRandomVehicleIdentificationNumber() {
        return faker.bothify("VHL###");
    }

    private static String getRandomVehicleEngineNumber() {
        return faker.bothify("ENG###");
    }

    private static String getRandomVehicleRegistrationNumber() {
        return faker.bothify("REG###");
    }

    private static String getRandomVehicleExternalColour() {
        return faker.color().name();
    }

    private static String getRandomAccountNumber() {
        return faker.number().digits(9);
    }

    private static String getRandomBSBNumber() {
        return faker.number().digits(6);
    }

    private static String getRandomSecurityLifeCycleStatus() {
        return GetRandomValue(new String[]{"New", "Used", "Demo"});
    }

    private static String getRandomSecurityType() {
        return GetRandomValue(new String[]{"Passenger Vehicle", "Light Commercial Vehicle", "Heavy Commercial Vehicle", "Other Vehicle"});
    }

    private static String getRandomPaymentMethod() {
        return GetRandomValue(new String[]{"Direct Debit", "Customer Initiated"});
    }

    private static String getRandomIndPhoneType() {
        return GetRandomValue(new String[]{"Mobile", "Home", "Work", "Alternate"});
    }

    private static String getRandomOrgPhoneType() {
        return GetRandomValue(new String[]{"Mobile", "Main", "Direct", "Alternate"});
    }

    private static String getRandomMobileNumber() {
        return faker.numerify("049157####");
    }

    private static String getRandomPhoneNumber() {
// Add "085550####" and "087010####" to this array once https://github.com/Toyota-Finance-Australia/qe-automation-java/issues/1032 is resolved
        return faker.numerify(GetRandomValue(new String[]{"025550####", "027010####", "035550####", "037010####", "075550####", "077010####"}));
    }

    private static String getRandomQuestIndAddressType() {
        return GetRandomValue(new String[]{"Residential", "Mailing"});
    }

    private static String getRandomQuestOrgAddressType() {
        return GetRandomValue(new String[]{"Mailing", "Registered Office"});
    }

    private static String getRandomAbn() {
        return GetRandomValue(new String[]{"80847612448", "50162134876", "97366291360", "90821560797", "70636684931"
                , "66666583179", "75133036969", "87626821529", "65135684847", "30150677795", "84007190043", "48614005453"
                , "29527007415", "33613942297", "69665045796", "90662118907", "30632794613", "57144756374", "45087772469"});
    }

    private static String getRandomCustomerId() {
        return GetRandomValue(new String[]{ "CUST-2011079","CUST-2011024","CUST-2011080","CUST-2011082"});
    }

    private static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    private static String getRandomEmailType() {
        return GetRandomValue(new String[]{"Personal", "Work"});
    }

    private static String getRandomCorrespondenceType() {
        return GetRandomValue(new String[]{"Email", "Letter"});
    }

    private static String getRandomYN() {
        return GetRandomValue(new String[]{"Y", "N"});
    }
}