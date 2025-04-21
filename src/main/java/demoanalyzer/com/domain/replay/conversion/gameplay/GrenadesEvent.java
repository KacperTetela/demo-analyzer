package demoanalyzer.com.domain.replay.conversion.gameplay;

public record GrenadesEvent(long tick, int roundNum, String thrower, String grenadeType) {}
