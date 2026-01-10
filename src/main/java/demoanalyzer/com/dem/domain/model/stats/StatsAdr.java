package demoanalyzer.com.dem.domain.model.stats;

public record StatsAdr(
    String name, Long steamId, Side side, Integer nRounds, Double dmg, Double adr) {}
