package demoanalyzer.com.domain.replay.conversion.gameplay;

public record TicksEvent(long tick, int roundNum, String side, String name) implements GameplayEvent {}
