package demoanalyzer.com.dem.parser.domain.model.raw;

import demoanalyzer.com.legacy.analyzer.GameplayEvent;

public record Grenades(long tick, int roundNum, String thrower, String grenadeType)
    implements GameplayEvent {}
