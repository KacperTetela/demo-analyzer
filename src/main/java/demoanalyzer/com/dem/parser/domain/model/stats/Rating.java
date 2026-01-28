package demoanalyzer.com.dem.parser.domain.model.stats;

public record Rating(
    String name, Long steamid, String side, int n_rounds, double impact, double rating) {}
