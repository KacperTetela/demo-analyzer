package demoanalyzer.com.domain.replay.conversion.gameplay;

public record InfernosEvent(long startTick, long endTick, int roundNum, String throwerPlace, String throwerName, String throwerSide) {}
