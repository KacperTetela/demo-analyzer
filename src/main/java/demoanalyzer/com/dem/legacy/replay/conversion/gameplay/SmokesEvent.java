package demoanalyzer.com.dem.legacy.replay.conversion.gameplay;

public record SmokesEvent(
    long startTick,
    double endTick,
    int roundNum,
    String throwerPlace,
    String throwerName,
    String throwerSide)
    implements GameplayEvent {}
