package demoanalyzer.com.dem.parser.domain.model.raw;

import demoanalyzer.com.dem.parser.infrastructure.GameplayEvent;

public record Grenades(long tick, int roundNum, String thrower, String grenadeType)
    implements GameplayEvent {}
