package demoanalyzer.com.domain.replay.conversion.header;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class HeaderDeserializer {


    public Optional<HeaderEvent> deserialize() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Path pathToJson = Paths.get("src/main/resources/analyzed/header.json");
            HeaderEvent attributes = mapper.readValue(pathToJson.toFile(), HeaderEvent.class);
            return Optional.of(attributes);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


}
