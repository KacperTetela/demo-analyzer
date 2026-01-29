package demoanalyzer.com.dem.parser.infrastructure;

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
  private final Map<String, String> csvToFieldNameMap;
  private final boolean isJavaRecord;
  private RecordComponent[] recordComponents;
  private Constructor<T> recordConstructor;

  public CsvDeserializer(Class<T> recordType) {
    this.recordType = recordType;
    this.fieldMap = new HashMap<>();
    this.csvToFieldNameMap = new HashMap<>();
    this.isJavaRecord = recordType.isRecord();

    if (isJavaRecord) {
      this.recordComponents = recordType.getRecordComponents();
      for (RecordComponent component : recordComponents) {
        String fieldName = component.getName();
        String snakeCaseName = convertCamelToSnakeCase(fieldName);
        csvToFieldNameMap.put(snakeCaseName.toLowerCase(), fieldName);
        csvToFieldNameMap.put(fieldName.toLowerCase(), fieldName);
      }
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
      for (Field field : recordType.getDeclaredFields()) {
        field.setAccessible(true);
        String fieldName = field.getName();
        fieldMap.put(fieldName.toLowerCase(), field);
        String snakeCaseName = convertCamelToSnakeCase(fieldName);
        csvToFieldNameMap.put(snakeCaseName.toLowerCase(), fieldName);
        csvToFieldNameMap.put(fieldName.toLowerCase(), fieldName);
      }
    }
  }

  private String convertCamelToSnakeCase(String camelCase) {
    return camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
  }

  public List<T> deserialize(BufferedReader reader)
      throws IOException, ReflectiveOperationException {
    List<T> result = new ArrayList<>();

    String headerLine = reader.readLine();
    if (headerLine == null) return result;

    String[] headers = headerLine.split(",");
    Map<String, Integer> headerIndexMap = new HashMap<>();
    for (int i = 0; i < headers.length; i++) {
      headerIndexMap.put(headers[i].trim().toLowerCase(), i);
    }

    String line;
    if (isJavaRecord) {
      while ((line = reader.readLine()) != null) {
        if (line.trim().isEmpty()) continue;
        result.add(parseLineForRecord(line, headerIndexMap));
      }
    } else {
      Map<Integer, Field> columnToFieldMap = mapColumnsToFields(headers);
      while ((line = reader.readLine()) != null) {
        if (line.trim().isEmpty()) continue;
        result.add(parseLineForClass(line, columnToFieldMap));
      }
    }
    return result;
  }

  private Map<Integer, Field> mapColumnsToFields(String[] headers) {
    Map<Integer, Field> columnMap = new HashMap<>();
    for (int i = 0; i < headers.length; i++) {
      String header = headers[i].trim().toLowerCase();
      String fieldName = csvToFieldNameMap.get(header);
      if (fieldName != null) {
        Field field = fieldMap.get(fieldName.toLowerCase());
        if (field != null) columnMap.put(i, field);
      } else {
        Field field = fieldMap.get(header);
        if (field != null) columnMap.put(i, field);
      }
    }
    return columnMap;
  }

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

  private T parseLineForRecord(String line, Map<String, Integer> headerIndexMap)
      throws ReflectiveOperationException {
    String[] values = line.split(",");
    Object[] constructorArgs = new Object[recordComponents.length];

    for (int i = 0; i < recordComponents.length; i++) {
      RecordComponent component = recordComponents[i];
      String componentName = component.getName();
      Class<?> componentType = component.getType();
      String snakeCaseName = convertCamelToSnakeCase(componentName);

      Integer columnIndex = headerIndexMap.get(componentName.toLowerCase());
      if (columnIndex == null) {
        columnIndex = headerIndexMap.get(snakeCaseName.toLowerCase());
      }

      if (columnIndex != null && columnIndex < values.length) {
        String value = values[columnIndex].trim();
        constructorArgs[i] = convertValueToType(value, componentType);
      } else {
        constructorArgs[i] = getDefaultValue(componentType);
      }
    }
    return recordConstructor.newInstance(constructorArgs);
  }

  private void setFieldValue(T instance, Field field, String value) throws IllegalAccessException {
    Class<?> fieldType = field.getType();
    try {
      Object convertedValue = convertValueToType(value, fieldType);
      if (convertedValue != null) field.set(instance, convertedValue);
    } catch (NumberFormatException e) {
      System.err.println("Conversion Error, Value '" + value + "' for " + field.getName());
    }
  }

  private Object convertValueToType(String value, Class<?> type) {
    if (value == null || value.isEmpty()) return getDefaultValue(type);
    if (type == String.class) return value;
    else if (type == int.class || type == Integer.class) return Integer.parseInt(value);
    else if (type == long.class || type == Long.class) return Long.parseLong(value);
    else if (type == double.class || type == Double.class) return Double.parseDouble(value);
    else if (type == float.class || type == Float.class) return Float.parseFloat(value);
    else if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
    return null;
  }

  private Object getDefaultValue(Class<?> type) {
    if (type == int.class) return 0;
    else if (type == long.class) return 0L;
    else if (type == double.class) return 0.0;
    else if (type == float.class) return 0.0f;
    else if (type == boolean.class) return false;
    else return null;
  }
}
