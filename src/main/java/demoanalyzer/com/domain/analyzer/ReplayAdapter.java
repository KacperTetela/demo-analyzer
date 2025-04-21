package demoanalyzer.com.domain.analyzer;

import demoanalyzer.com.domain.replay.conversion.gameplay.GameplayEvent;

import java.util.List;

public interface ReplayAdapter {

  BasicDTO getBasicReplayInfo();

  // Generic method - returns all events of a specific type
  <T extends GameplayEvent> List<T> getGameplayEvents(Class<T> eventType);
}
