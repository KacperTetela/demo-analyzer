package demoanalyzer.com.domain.conversion.header;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class HeaderDeserializer {


    public Optional<HeaderAttributes> deserialize() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Path pathToJson = Paths.get("src/main/resources/analyzed/header.json");
            HeaderAttributes attributes = mapper.readValue(pathToJson.toFile(), HeaderAttributes.class);
            return Optional.of(attributes);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


}
