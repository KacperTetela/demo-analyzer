package demoanalyzer.com.legacy.analyzer;

import demoanalyzer.com.dem.parser.domain.model.raw.GameInfo;
import demoanalyzer.com.dem.parser.infrastructure.GameplayDeserializer;
import demoanalyzer.com.dem.parser.infrastructure.HeaderDeserializer;
import demoanalyzer.com.dem.parser.domain.model.header.Header;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomainReplayServiceAdapter implements ReplayAdapter {
  private final GameplayDeserializer gameplayDeserializer;

  public DomainReplayServiceAdapter() {
    gameplayDeserializer = new GameplayDeserializer();
  }

  public GameInfo getBasicReplayInfo() {
    HeaderDeserializer headerDeserializer = new HeaderDeserializer();
    Optional<Header> headerEventOptional = headerDeserializer.deserialize();
    String mapName = headerEventOptional.get().map_name();
    String serverName = headerEventOptional.get().server_name();
    return new GameInfo(mapName, serverName);
  }

  @Override
  public <T extends GameplayEvent> List<T> getGameplayEvents(Class<T> eventType) {
    GameplayDeserializer deserializer = new GameplayDeserializer();
    return deserializer.processSpecificEventType(eventType);
  }
}
