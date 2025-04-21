package demoanalyzer.com.domain.replay.conversion.gameplay;

public record BombEvent(long tick, int roundNum, String event, String name, String bombsite) {}
