package demoanalyzer.com.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvDeserializer<T> {
  private final Class<T> recordType;
  private final Map<String, Field> fieldMap;
  private final Map<String, String> csvToFieldNameMap; // Mapa nazw CSV -> nazwy pól Java
  private final boolean isJavaRecord;
  private RecordComponent[] recordComponents;
  private Constructor<T> recordConstructor;

  /**
   * Constructor of the CsvDeserializer class.
   *
   * @param recordType the Record class type to which the CSV data will be mapped
   */
  public CsvDeserializer(Class<T> recordType) {
    this.recordType = recordType;
    this.fieldMap = new HashMap<>();
    this.csvToFieldNameMap = new HashMap<>();

    // Sprawdź, czy klasa jest Java Record
    this.isJavaRecord = recordType.isRecord();

    if (isJavaRecord) {
      // Inicjalizacja dla Java Records
      this.recordComponents = recordType.getRecordComponents();

      // Tworzenie mapy nazw komponentów rekordu
      for (RecordComponent component : recordComponents) {
        String fieldName = component.getName();

        // Automatyczna konwersja camelCase -> snake_case
        String snakeCaseName = convertCamelToSnakeCase(fieldName);
        csvToFieldNameMap.put(snakeCaseName.toLowerCase(), fieldName);

        // Zachowaj także oryginalną nazwę jako mapowanie
        csvToFieldNameMap.put(fieldName.toLowerCase(), fieldName);
      }

      // Znajdź konstruktor kanoniczny
      try {
        Class<?>[] paramTypes = new Class<?>[recordComponents.length];
        for (int i = 0; i < recordComponents.length; i++) {
          paramTypes[i] = recordComponents[i].getType();
        }
        this.recordConstructor = recordType.getDeclaredConstructor(paramTypes);
      } catch (NoSuchMethodException e) {
        throw new RuntimeException(
            "Could not find canonical constructor for record: " + recordType.getName(), e);
      }
    } else {
      // Inicjalizacja dla tradycyjnych klas
      for (Field field : recordType.getDeclaredFields()) {
        field.setAccessible(true);
        String fieldName = field.getName();
        fieldMap.put(fieldName.toLowerCase(), field);

        // Automatyczna konwersja camelCase -> snake_case
        String snakeCaseName = convertCamelToSnakeCase(fieldName);
        csvToFieldNameMap.put(snakeCaseName.toLowerCase(), fieldName);

        // Zachowaj także oryginalną nazwę jako mapowanie
        csvToFieldNameMap.put(fieldName.toLowerCase(), fieldName);
      }
    }
  }

  /**
   * Converts a camelCase string to snake_case. For example: "attackerHealth" becomes
   * "attacker_health"
   *
   * @param camelCase the camelCase string
   * @return the snake_case equivalent
   */
  private String convertCamelToSnakeCase(String camelCase) {
    return camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
  }

  /**
   * Loads a CSV file and converts it into a list of objects of type T.
   *
   * @param filePath the path to the CSV file
   * @return a list of objects of type T
   * @throws IOException if an error occurs while reading the file
   * @throws ReflectiveOperationException if an error occurs while creating the objects
   */
  public List<T> deserialize(String filePath) throws IOException, ReflectiveOperationException {
    List<T> result = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String headerLine = reader.readLine();
      if (headerLine == null) {
        throw new IOException("Empty CSV File");
      }

      String[] headers = headerLine.split(",");

      // Mapuj nagłówki CSV do ich indeksów
      Map<String, Integer> headerIndexMap = new HashMap<>();
      for (int i = 0; i < headers.length; i++) {
        headerIndexMap.put(headers[i].trim().toLowerCase(), i);
      }

      if (isJavaRecord) {
        String line;
        while ((line = reader.readLine()) != null) {
          result.add(parseLineForRecord(line, headerIndexMap));
        }
      } else {
        Map<Integer, Field> columnToFieldMap = mapColumnsToFields(headers);
        String line;
        while ((line = reader.readLine()) != null) {
          result.add(parseLineForClass(line, columnToFieldMap));
        }
      }
    }

    return result;
  }

  /**
   * Maps CSV columns to class fields.
   *
   * @param headers the array of CSV headers
   * @return a map of column indexes to Field objects
   */
  private Map<Integer, Field> mapColumnsToFields(String[] headers) {
    Map<Integer, Field> columnMap = new HashMap<>();

    for (int i = 0; i < headers.length; i++) {
      String header = headers[i].trim().toLowerCase();

      // Spróbuj znaleźć mapowanie nazwy CSV na nazwę pola Java
      String fieldName = csvToFieldNameMap.get(header);
      if (fieldName != null) {
        Field field = fieldMap.get(fieldName.toLowerCase());
        if (field != null) {
          columnMap.put(i, field);
        }
      } else {
        // Bezpośrednie dopasowanie, jeśli nie ma mapowania
        Field field = fieldMap.get(header);
        if (field != null) {
          columnMap.put(i, field);
        }
      }
    }

    return columnMap;
  }

  /**
   * Parses a CSV row into an object of type T when T is a regular class.
   *
   * @param line the CSV data row
   * @param columnMap a map of column indexes to Field objects
   * @return an object of type T with populated data
   * @throws ReflectiveOperationException if an error occurs while creating the object
   */
  private T parseLineForClass(String line, Map<Integer, Field> columnMap)
      throws ReflectiveOperationException {
    String[] values = line.split(",");
    T instance = recordType.getDeclaredConstructor().newInstance();

    for (Map.Entry<Integer, Field> entry : columnMap.entrySet()) {
      int columnIndex = entry.getKey();
      Field field = entry.getValue();

      if (columnIndex < values.length) {
        String value = values[columnIndex].trim();
        setFieldValue(instance, field, value);
      }
    }

    return instance;
  }

  /**
   * Parses a CSV row into an object of type T when T is a Java Record.
   *
   * @param line the CSV data row
   * @param headerIndexMap a map of header names to their column indexes
   * @return a record of type T with populated data
   * @throws ReflectiveOperationException if an error occurs while creating the record
   */
  private T parseLineForRecord(String line, Map<String, Integer> headerIndexMap)
      throws ReflectiveOperationException {
    String[] values = line.split(",");
    Object[] constructorArgs = new Object[recordComponents.length];

    // Wypełnij tablicę argumentów konstruktora
    for (int i = 0; i < recordComponents.length; i++) {
      RecordComponent component = recordComponents[i];
      String componentName = component.getName();
      Class<?> componentType = component.getType();

      // Konwertuj nazwę pola na snake_case dla porównania
      String snakeCaseName = convertCamelToSnakeCase(componentName);

      // Sprawdź, czy istnieje kolumna CSV o tej samej nazwie (camelCase lub snake_case)
      Integer columnIndex = headerIndexMap.get(componentName.toLowerCase());
      if (columnIndex == null) {
        // Sprawdź wersję snake_case
        columnIndex = headerIndexMap.get(snakeCaseName.toLowerCase());
      }

      if (columnIndex != null && columnIndex < values.length) {
        String value = values[columnIndex].trim();
        constructorArgs[i] = convertValueToType(value, componentType);
      } else {
        // Jeśli nie znaleziono kolumny, użyj wartości domyślnej
        constructorArgs[i] = getDefaultValue(componentType);
      }
    }

    return recordConstructor.newInstance(constructorArgs);
  }

  /**
   * Sets the value of a field in an object based on the field's type.
   *
   * @param instance the object whose field should be set
   * @param field the field to be set
   * @param value the value as a String
   * @throws IllegalAccessException if there is an error accessing the field
   */
  private void setFieldValue(T instance, Field field, String value) throws IllegalAccessException {
    Class<?> fieldType = field.getType();
    try {
      Object convertedValue = convertValueToType(value, fieldType);
      if (convertedValue != null) {
        field.set(instance, convertedValue);
      }
    } catch (NumberFormatException e) {
      System.err.println("Conversion Error, Value '" + value + "' for " + field.getName());
    }
  }

  /**
   * Converts a string value to the specified type.
   *
   * @param value the string value to convert
   * @param type the target type
   * @return the converted value
   */
  private Object convertValueToType(String value, Class<?> type) {
    if (value == null || value.isEmpty()) {
      return getDefaultValue(type);
    }

    if (type == String.class) {
      return value;
    } else if (type == int.class || type == Integer.class) {
      return Integer.parseInt(value);
    } else if (type == long.class || type == Long.class) {
      return Long.parseLong(value);
    } else if (type == double.class || type == Double.class) {
      return Double.parseDouble(value);
    } else if (type == float.class || type == Float.class) {
      return Float.parseFloat(value);
    } else if (type == boolean.class || type == Boolean.class) {
      return Boolean.parseBoolean(value);
    }

    return null;
  }

  /**
   * Returns the default value for a given type.
   *
   * @param type the type to get a default value for
   * @return the default value (0, false, or null)
   */
  private Object getDefaultValue(Class<?> type) {
    if (type == int.class) return 0;
    else if (type == long.class) return 0L;
    else if (type == double.class) return 0.0;
    else if (type == float.class) return 0.0f;
    else if (type == boolean.class) return false;
    else return null; // Dla typów obiektowych
  }
}
