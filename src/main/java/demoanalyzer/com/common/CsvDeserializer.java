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

    this.isJavaRecord = recordType.isRecord();

    if (isJavaRecord) {
      this.recordComponents = recordType.getRecordComponents();

      for (RecordComponent component : recordComponents) {
        fieldMap.put(component.getName().toLowerCase(), null);
      }

      // Find canonical constructor
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
      // Initialization for traditional classes**
      for (Field field : recordType.getDeclaredFields()) {
        field.setAccessible(true);
        fieldMap.put(field.getName().toLowerCase(), field);
      }
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

      // In the case of records, we need to know the mapping of CSV headers to the constructor parameters
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
      Field field = fieldMap.get(header);
      if (field != null) {
        columnMap.put(i, field);
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

    for (int i = 0; i < recordComponents.length; i++) {
      String componentName = recordComponents[i].getName().toLowerCase();
      Integer columnIndex = headerIndexMap.get(componentName);

      if (columnIndex != null && columnIndex < values.length) {
        String value = values[columnIndex].trim();
        constructorArgs[i] = convertValueToType(value, recordComponents[i].getType());
      } else {
        constructorArgs[i] = getDefaultValue(recordComponents[i].getType());
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
    else return null;
  }
}
