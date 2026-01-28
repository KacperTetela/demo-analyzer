package demoanalyzer.com.dem.parser.domain.model.raw;

import demoanalyzer.com.dem.parser.infrastructure.GameplayEvent;

public record Smokes(
    long startTick,
    double endTick,
    int roundNum,
    String throwerPlace,
    String throwerName,
    String throwerSide)
    implements GameplayEvent {}
