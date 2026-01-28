package demoanalyzer.com.dem.parser.domain.model.raw;

import demoanalyzer.com.dem.parser.infrastructure.GameplayEvent;

public record Infernos(
    long startTick,
    long endTick,
    int roundNum,
    String throwerPlace,
    String throwerName,
    String throwerSide)
    implements GameplayEvent {}
