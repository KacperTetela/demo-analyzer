package demoanalyzer.com.domain.replay;

import demoanalyzer.com.domain.analyzer.BasicDTO;
import demoanalyzer.com.domain.analyzer.ReplayAdapter;
import demoanalyzer.com.domain.replay.conversion.gameplay.GameplayDeserializer;
import demoanalyzer.com.domain.replay.conversion.gameplay.KillsEvent;
import demoanalyzer.com.domain.replay.conversion.gameplay.RoundsEvent;
import demoanalyzer.com.domain.replay.conversion.header.HeaderDeserializer;
import demoanalyzer.com.domain.replay.conversion.header.HeaderEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReplayService implements ReplayAdapter {

  public BasicDTO getBasicReplayInfo() {
    HeaderDeserializer headerDeserializer = new HeaderDeserializer();
    Optional<HeaderEvent> headerEventOptional = headerDeserializer.deserialize();
    String mapName = headerEventOptional.get().map_name();
    String serverName = headerEventOptional.get().server_name();
    return new BasicDTO(mapName, serverName);
  }

  private List<KillsEvent> getKillsInfo() {
    GameplayDeserializer gameplayDeserializer = new GameplayDeserializer();
    gameplayDeserializer.processAllCsvFiles();

    return new ArrayList<KillsEvent>();
  }

  private List<RoundsEvent> getRoundsInfo() {
    GameplayDeserializer gameplayDeserializer = new GameplayDeserializer();
    gameplayDeserializer.processAllCsvFiles();

    return new ArrayList<RoundsEvent>();
  }
}
