package wrapper.utilities;

import com.google.common.base.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.*;
import org.openqa.selenium.NotFoundException;
import java.io.*;
import java.util.*;

public class CsvHelper {

    public static final Logger Log = LogManager.getLogger(CsvHelper.class.getName());

    public static String GetDataFromCsv(String csvFilePath, String uniqueFieldName, String uniqueFieldValue, String targetFieldName) {
        try {
            var file = new File(csvFilePath);
            byte[] bytes = FileUtils.readFileToByteArray(file);
            var data = new String(bytes);
            data = StringUtils.replaceChars(data, "\r", "");
            var dataArray = data.split("\n");
            var keys = dataArray[0];
            var outerMap = new HashMap<String, Map<String, String>>();
            var keyArr = keys.split(",");
            var keysFromFile = new ArrayList<>(Arrays.asList(keyArr));
            var uniqueFieldIndex = keysFromFile.indexOf(uniqueFieldName);
            keysFromFile.remove(uniqueFieldIndex);
            for (var d = 1; d < dataArray.length; d++) {
                var mp = new HashMap<String, String>();
                var rowArr = dataArray[d].split(",", -1);
                var row = new ArrayList<>(Arrays.asList(rowArr));
                var keyForTestCase = row.get(uniqueFieldIndex);
                row.remove(uniqueFieldIndex);
                for (var i = 0; i < keysFromFile.size(); i++) {
                    mp.put(keysFromFile.get(i).trim(), row.get(i).trim());
                }
                outerMap.put(keyForTestCase, mp);
            }
            var rowMap = outerMap.get(uniqueFieldValue);
            var targetFieldValue = rowMap.get(targetFieldName);
            if (Strings.isNullOrEmpty(targetFieldValue)) {
                throw new NotFoundException("Unable to find matching record");
            }
            Log.debug("The value of %s is fetched as %s".formatted(targetFieldName, targetFieldValue));
            return targetFieldValue;
        } catch (Exception e) {
            var strException = e.getMessage();
            if (strException.contains("Unable to find matching record")) {
                Log.info("Unable to find matching record on %s".formatted(targetFieldName));
            } else {
                Log.error("Unable to Read Data from file %s due to %s".formatted(csvFilePath, strException));
            }
            return null;
        }
    }

    public static List<String> GetValuesAsArray(String csvFilePath) {
        List<String> records = new ArrayList<>();
        try (var br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.toString(values));
            }
            Log.info("Number of Records fetched is : %s".formatted(records.size()));
        } catch (Exception ex) {
            Log.error("Unable to Read Data from file %s due to %s".formatted(csvFilePath, ex.getMessage()));
        }
        return records;
    }
}
