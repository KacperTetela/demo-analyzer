package demoanalyzer.com.legacy.replay.conversion.raw;

public record InfernosEvent(
    long startTick,
    long endTick,
    int roundNum,
    String throwerPlace,
    String throwerName,
    String throwerSide)
    implements GameplayEvent {}
