package demoanalyzer.com.dem.parser.domain.model.raw;


public record Smokes(
    long startTick,
    double endTick,
    int roundNum,
    String throwerPlace,
    String throwerName,
    String throwerSide)
     {}
