package demoanalyzer.com.legacy.replay;

import demoanalyzer.com.legacy.analyzer.ReplayAdapter;
import demoanalyzer.com.legacy.replay.conversion.gameplay.GameInfo;
import demoanalyzer.com.legacy.replay.conversion.gameplay.GameplayDeserializer;
import demoanalyzer.com.legacy.replay.conversion.gameplay.GameplayEvent;
import demoanalyzer.com.legacy.replay.conversion.header.HeaderDeserializer;
import demoanalyzer.com.legacy.replay.conversion.header.HeaderEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomainReplayService implements ReplayAdapter {
  private final GameplayDeserializer gameplayDeserializer;

  public DomainReplayService() {
    gameplayDeserializer = new GameplayDeserializer();
  }

  public GameInfo getBasicReplayInfo() {
    HeaderDeserializer headerDeserializer = new HeaderDeserializer();
    Optional<HeaderEvent> headerEventOptional = headerDeserializer.deserialize();
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
