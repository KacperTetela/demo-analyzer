package demoanalyzer.com.dem.parser.domain.model.raw;

import demoanalyzer.com.legacy.analyzer.GameplayEvent;

public record Shots(long tick, int roundNum) implements GameplayEvent {}
