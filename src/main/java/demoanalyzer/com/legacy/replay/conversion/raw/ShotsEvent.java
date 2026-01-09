package demoanalyzer.com.legacy.replay.conversion.raw;

public record ShotsEvent(long tick, int roundNum) implements GameplayEvent {}
