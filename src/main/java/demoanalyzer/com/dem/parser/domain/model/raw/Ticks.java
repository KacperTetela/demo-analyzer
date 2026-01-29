package demoanalyzer.com.dem.parser.domain.model.raw;

import demoanalyzer.com.legacy.analyzer.GameplayEvent;

public record Ticks(long tick, int roundNum, String side, String name) implements GameplayEvent {}
