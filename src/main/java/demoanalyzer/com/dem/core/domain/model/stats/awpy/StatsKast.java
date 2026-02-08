package demoanalyzer.com.dem.core.domain.model.stats.awpy;

public record StatsKast(
    String name, Long steamId, Side side, Integer nRounds, Integer kastRounds, Double kast) {}
