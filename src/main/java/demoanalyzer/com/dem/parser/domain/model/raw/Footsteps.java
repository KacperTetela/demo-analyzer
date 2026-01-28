package demoanalyzer.com.dem.parser.domain.model.raw;

import demoanalyzer.com.dem.parser.infrastructure.GameplayEvent;

public record Footsteps(
    long tick, int roundNum, String playerPlace, String playerName, String playerSide)
    implements GameplayEvent {}
