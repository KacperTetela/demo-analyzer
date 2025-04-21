package demoanalyzer.com.domain.replay.conversion.gameplay;

import demoanalyzer.com.common.CsvDeserializer;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameplayDeserializer {

  private static final String DATA_DIRECTORY = "src\\main\\resources\\analyzed";

  // A map mapping file names to the appropriate record classes
  private final Map<String, Class<?>> recordTypeMap;

  public GameplayDeserializer() {
    this.recordTypeMap = initializeRecordTypeMap();
  }

  /**
   * Initializes the map of record types
   *
   * @return a map of file_name -> record_class
   */
  private Map<String, Class<?>> initializeRecordTypeMap() {
    Map<String, Class<?>> map = new HashMap<>();
    map.put("bomb", BombEvent.class);
    map.put("damages", DamagesEvent.class);
    map.put("footsteps", FootstepsEvent.class);
    map.put("grenades", GrenadesEvent.class);
    map.put("infernos", InfernosEvent.class);
    map.put("kills", KillsEvent.class);
    map.put("rounds", RoundsEvent.class);
    map.put("shots", ShotsEvent.class);
    map.put("smokes", SmokesEvent.class);
    map.put("ticks", TicksEvent.class);

    return map;
  }

  /** Processes all CSV files in the directory */
  public void processAllCsvFiles() {
    File directory = new File(DATA_DIRECTORY);
    File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

    if (files == null || files.length == 0) {
      System.out.println("No CSV files found in the directory: " + DATA_DIRECTORY);
      return;
    }

    for (File file : files) {
      String fileName = file.getName();
      String baseName = fileName.substring(0, fileName.lastIndexOf("."));

      processFile(file.getAbsolutePath(), baseName);
    }
  }

  /**
   * Processes a single CSV file
   *
   * @param filePath the path to the file
   * @param baseName the base name of the file (without extension)
   */
  private void processFile(String filePath, String baseName) {
    Class<?> recordType = recordTypeMap.get(baseName);
    if (recordType == null) {
      System.out.println("No configuration found for file: " + baseName);
      return;
    }

    try {
      System.out.println("Processing file: " + filePath);
      CsvDeserializer<?> deserializer = new CsvDeserializer<>(recordType);
      List<?> records = deserializer.deserialize(filePath);

      System.out.println("Loaded " + records.size() + " records from " + baseName + ".csv");

      // For example, display the first 5 records
      int count = 0;
      for (Object record : records) {
        if (count < 5) {
          System.out.println(record);
          count++;
        } else {
          break;
        }
      }
      System.out.println("-----------------------------------");

    } catch (Exception e) {
      System.err.println("Error processing file" + baseName + ".csv: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
