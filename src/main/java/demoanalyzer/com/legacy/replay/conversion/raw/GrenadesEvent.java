package demoanalyzer.com.legacy.replay.conversion.raw;

public record GrenadesEvent(long tick, int roundNum, String thrower, String grenadeType)
    implements GameplayEvent {}
