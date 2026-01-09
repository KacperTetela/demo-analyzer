package demoanalyzer.com.legacy.replay.conversion.raw;

public record FootstepsEvent(
    long tick, int roundNum, String playerPlace, String playerName, String playerSide)
    implements GameplayEvent {}
