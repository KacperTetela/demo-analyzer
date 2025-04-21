package demoanalyzer.com.domain.replay.conversion.gameplay;

public record RoundsEvent(
    long start, long freeze_end, long end, long official_end, int roundNumm, String winner, String reason,
    double bombPlant, String bombSite) {}
