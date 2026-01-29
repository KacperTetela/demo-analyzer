package demoanalyzer.com.dem.parser.domain.model.raw;

import demoanalyzer.com.legacy.analyzer.GameplayEvent;

public record Smokes(
    long startTick,
    double endTick,
    int roundNum,
    String throwerPlace,
    String throwerName,
    String throwerSide)
    implements GameplayEvent {}
