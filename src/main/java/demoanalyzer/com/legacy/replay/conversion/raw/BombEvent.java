package demoanalyzer.com.legacy.replay.conversion.raw;

public record BombEvent(long tick, int roundNum, String event, String name, String bombsite)
    implements GameplayEvent {}
