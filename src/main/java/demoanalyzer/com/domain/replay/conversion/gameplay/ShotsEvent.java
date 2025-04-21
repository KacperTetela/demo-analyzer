package demoanalyzer.com.domain.replay.conversion.gameplay;

public record ShotsEvent(long tick, int roundNum) implements GameplayEvent {}
