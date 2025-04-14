package demoanalyzer.com.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvDeserializer<T> {
  private final Class<T> recordType;
  private final Map<String, Field> fieldMap;

  /**
   * Constructor of the CsvDeserializer class.
   *
   * @param recordType the Record class type to which the CSV data will be mapped
   */
  public CsvDeserializer(Class<T> recordType) {
    this.recordType = recordType;
    this.fieldMap = new HashMap<>();

    // Mapowanie nazw pól klasy do obiektów Field
    for (Field field : recordType.getDeclaredFields()) {
      field.setAccessible(true);
      fieldMap.put(field.getName().toLowerCase(), field);
    }
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
      Map<Integer, Field> columnToFieldMap = mapColumnsToFields(headers);

      String line;
      while ((line = reader.readLine()) != null) {
        result.add(parseLine(line, columnToFieldMap));
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
      Field field = fieldMap.get(header);
      if (field != null) {
        columnMap.put(i, field);
      }
    }

    return columnMap;
  }

  /**
   * Parses a CSV row into an object of type T.
   *
   * @param line the CSV data row
   * @param columnMap a map of column indexes to Field objects
   * @return an object of type T with populated data
   * @throws ReflectiveOperationException if an error occurs while creating the object
   */
  private T parseLine(String line, Map<Integer, Field> columnMap)
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
      if (fieldType == String.class) {
        field.set(instance, value);
      } else if (fieldType == int.class || fieldType == Integer.class) {
        field.set(instance, Integer.parseInt(value));
      } else if (fieldType == long.class || fieldType == Long.class) {
        field.set(instance, Long.parseLong(value));
      } else if (fieldType == double.class || fieldType == Double.class) {
        field.set(instance, Double.parseDouble(value));
      } else if (fieldType == float.class || fieldType == Float.class) {
        field.set(instance, Float.parseFloat(value));
      } else if (fieldType == boolean.class || fieldType == Boolean.class) {
        field.set(instance, Boolean.parseBoolean(value));
      }
    } catch (NumberFormatException e) {
      System.err.println("Conversion Error, Value '" + value + "' for " + field.getName());
    }
  }
}
