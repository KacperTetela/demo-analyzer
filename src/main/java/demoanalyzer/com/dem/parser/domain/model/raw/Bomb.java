package demoanalyzer.com.dem.parser.domain.model.raw;

import demoanalyzer.com.dem.parser.infrastructure.GameplayEvent;

public record Bomb(long tick, int roundNum, String event, String name, String bombsite)
    implements GameplayEvent {}
