package demoanalyzer.com.dem.parser.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import demoanalyzer.com.dem.parser.domain.model.header.Header;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class HeaderDeserializer {

  public Optional<Header> deserialize() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      Path pathToJson = Paths.get("src/main/resources/analyzed/header.json");
      Header attributes = mapper.readValue(pathToJson.toFile(), Header.class);
      return Optional.of(attributes);
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }
}
