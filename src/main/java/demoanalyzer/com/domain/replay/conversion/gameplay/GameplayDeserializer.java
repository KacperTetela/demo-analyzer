package demoanalyzer.com.domain.replay.conversion.gameplay;

import demoanalyzer.com.common.CsvDeserializer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameplayDeserializer {

  private static final String DATA_DIRECTORY = "src\\main\\resources\\analyzed";

  // A map mapping file names to the appropriate record classes
  private final Map<String, Class<? extends GameplayEvent>> recordTypeMap;

  public GameplayDeserializer() {
    this.recordTypeMap = initializeRecordTypeMap();
  }

  /**
   * Initializes the map of record types
   *
   * @return a map of file_name -> record_class
   */
  private Map<String, Class<? extends GameplayEvent>> initializeRecordTypeMap() {
    Map<String, Class<? extends GameplayEvent>> map = new HashMap<>();
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

  /**
   * Processes CSV files for a specific event type and returns matching GameplayEvents
   *
   * @param eventType Class of the event type to process
   * @return List of GameplayEvents of the specified type
   */
  public <T extends GameplayEvent> List<T> processSpecificEventType(Class<T> eventType) {
    List<T> events = new ArrayList<>();

    // Find the base name for this event type
    String baseName = null;
    for (Map.Entry<String, Class<? extends GameplayEvent>> entry : recordTypeMap.entrySet()) {
      if (entry.getValue().equals(eventType)) {
        baseName = entry.getKey();
        break;
      }
    }

    if (baseName == null) {
      System.out.println("No base name found for event type: " + eventType.getSimpleName());
      return events;
    }

    File directory = new File(DATA_DIRECTORY);
    File file = new File(directory, baseName + ".csv");

    if (!file.exists()) {
      System.out.println("CSV file not found: " + file.getAbsolutePath());
      return events;
    }

    return processFile(file.getAbsolutePath(), baseName, eventType);
  }

  /**
   * Processes a single CSV file for a specific event type
   *
   * @param filePath the path to the file
   * @param baseName the base name of the file (without extension)
   * @param eventType Class of the event type to process
   * @return List of GameplayEvents of the specified type
   */
  private <T extends GameplayEvent> List<T> processFile(
      String filePath, String baseName, Class<T> eventType) {
    List<T> result = new ArrayList<>();

    Class<? extends GameplayEvent> recordType = recordTypeMap.get(baseName);
    if (recordType == null || !recordType.equals(eventType)) {
      System.out.println(
          "Invalid configuration for file: "
              + baseName
              + " and type: "
              + eventType.getSimpleName());
      return result;
    }

    try {
      System.out.println("Processing file: " + filePath);
      CsvDeserializer<T> deserializer = new CsvDeserializer<>(eventType);
      List<T> records = deserializer.deserialize(filePath);

      System.out.println("Loaded " + records.size() + " records from " + baseName + ".csv");

      // Add all records to the result list
      result.addAll(records);

      // For debug purposes, display the first 5 records
      int count = 0;
      for (T record : result) {
        if (count < 5) {
          System.out.println(record);
          count++;
        } else {
          break;
        }
      }
      System.out.println("-----------------------------------");

    } catch (Exception e) {
      System.err.println("Error processing file " + baseName + ".csv: " + e.getMessage());
      e.printStackTrace();
    }

    return result;
  }
}
