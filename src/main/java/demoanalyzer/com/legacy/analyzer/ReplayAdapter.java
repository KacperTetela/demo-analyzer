package demoanalyzer.com.legacy.analyzer;

import demoanalyzer.com.dem.parser.domain.model.raw.GameInfo;

import java.util.List;

public interface ReplayAdapter {

  GameInfo getBasicReplayInfo();

  // Generic method - returns all events of a specific type
  <T extends GameplayEvent> List<T> getGameplayEvents(Class<T> eventType);
}
