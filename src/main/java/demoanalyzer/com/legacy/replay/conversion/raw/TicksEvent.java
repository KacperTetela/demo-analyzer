package demoanalyzer.com.legacy.replay.conversion.raw;

public record TicksEvent(long tick, int roundNum, String side, String name) implements GameplayEvent {}
