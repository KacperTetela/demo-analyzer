package demoanalyzer.com.domain.replay.conversion.gameplay;

public record FootstepsEvent(
    long tick, int roundNum, String playerPlace, String playerName, String playerSide) {}
