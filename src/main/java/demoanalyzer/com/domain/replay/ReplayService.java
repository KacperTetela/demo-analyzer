package demoanalyzer.com.domain.replay;

import demoanalyzer.com.domain.analyzer.GameDetailsDTO;
import demoanalyzer.com.domain.analyzer.ReplayAdapter;
import demoanalyzer.com.domain.replay.conversion.gameplay.GameInfo;
import demoanalyzer.com.domain.replay.conversion.gameplay.GameplayDeserializer;
import demoanalyzer.com.domain.replay.conversion.gameplay.GameplayEvent;
import demoanalyzer.com.domain.replay.conversion.header.HeaderDeserializer;
import demoanalyzer.com.domain.replay.conversion.header.HeaderEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReplayService implements ReplayAdapter {
  private final GameplayDeserializer gameplayDeserializer;

  public ReplayService() {
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
