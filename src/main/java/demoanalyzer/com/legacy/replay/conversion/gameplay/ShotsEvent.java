package demoanalyzer.com.legacy.replay.conversion.gameplay;

public record ShotsEvent(long tick, int roundNum) implements GameplayEvent {}
