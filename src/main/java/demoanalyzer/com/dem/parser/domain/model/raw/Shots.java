package demoanalyzer.com.dem.parser.domain.model.raw;

import demoanalyzer.com.dem.parser.infrastructure.GameplayEvent;

public record Shots(long tick, int roundNum) implements GameplayEvent {}
