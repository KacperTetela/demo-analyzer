package demoanalyzer.com.domain.replay.conversion;

public record BombEvent(long tick, String event, String name, String bombsite, int roundNum) {}
