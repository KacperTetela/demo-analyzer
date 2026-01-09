package demoanalyzer.com.legacy.replay.conversion.stats;

public record Kast(
    String name, Long steamid, String side, int n_rounds, int kast_rounds, double kast) {}
