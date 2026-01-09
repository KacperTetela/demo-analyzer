package demoanalyzer.com.dem.domain.model.stats;

public record StatsKast(
    String name, Long steamId, String side, int nRounds, int kastRounds, double kast) {}
