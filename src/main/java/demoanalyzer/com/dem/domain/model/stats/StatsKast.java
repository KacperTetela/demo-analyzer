package demoanalyzer.com.dem.domain.model.stats;

public record StatsKast(
    String name, Long steamId, Side side, Integer nRounds, Integer kastRounds, Integer kast) {}
