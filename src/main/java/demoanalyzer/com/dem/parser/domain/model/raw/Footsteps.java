package demoanalyzer.com.dem.parser.domain.model.raw;

import demoanalyzer.com.legacy.analyzer.GameplayEvent;

public record Footsteps(
    long tick, int roundNum, String playerPlace, String playerName, String playerSide)
    implements GameplayEvent {}
