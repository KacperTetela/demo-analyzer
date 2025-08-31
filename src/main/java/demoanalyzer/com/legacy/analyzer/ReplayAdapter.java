package demoanalyzer.com.legacy.analyzer;

import demoanalyzer.com.legacy.replay.conversion.gameplay.GameInfo;
import demoanalyzer.com.legacy.replay.conversion.gameplay.GameplayEvent;

import java.util.List;

public interface ReplayAdapter {

  GameInfo getBasicReplayInfo();

  // Generic method - returns all events of a specific type
  <T extends GameplayEvent> List<T> getGameplayEvents(Class<T> eventType);
}
