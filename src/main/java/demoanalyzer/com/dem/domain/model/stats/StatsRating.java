package demoanalyzer.com.dem.domain.model.stats;

public record StatsRating(
    String name, Long steamId, String side, int nRounds, double impact, double rating) {}
