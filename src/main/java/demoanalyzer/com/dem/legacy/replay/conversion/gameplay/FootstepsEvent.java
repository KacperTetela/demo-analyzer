package demoanalyzer.com.dem.legacy.replay.conversion.gameplay;

public record FootstepsEvent(
    long tick, int roundNum, String playerPlace, String playerName, String playerSide)
    implements GameplayEvent {}
