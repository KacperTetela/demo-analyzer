package demoanalyzer.com.dem.domain.model.stats;

public record StatsAdr(
    String name, Long steamId, String side, int nRounds, double dmg, double adr) {}
