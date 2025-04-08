package demoanalyzer.com.domain.replay;

import demoanalyzer.com.domain.replay.conversion.header.HeaderDeserializer;
import demoanalyzer.com.domain.replay.conversion.header.HeaderEvent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReplayService {
    public String getMapName() {
        HeaderDeserializer headerDeserializer = new HeaderDeserializer();
        Optional<HeaderEvent> headerEventOptional = headerDeserializer.deserialize();

        return headerEventOptional.get().map_name();
    }
}
