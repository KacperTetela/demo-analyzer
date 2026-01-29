package demoanalyzer.com.dem.parser.domain.model.raw;

import demoanalyzer.com.legacy.analyzer.GameplayEvent;

public record Bomb(long tick, int roundNum, String event, String name, String bombsite)
    implements GameplayEvent {}
