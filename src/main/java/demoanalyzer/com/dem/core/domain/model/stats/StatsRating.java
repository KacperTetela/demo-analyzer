package demoanalyzer.com.dem.core.domain.model.stats;

public record StatsRating(
    String name, Long steamId, Side side, Integer nRounds, Double impact, Double rating) {}
