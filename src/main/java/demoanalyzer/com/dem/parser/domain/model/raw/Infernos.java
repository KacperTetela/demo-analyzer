package demoanalyzer.com.dem.parser.domain.model.raw;


public record Infernos(
    long startTick,
    long endTick,
    int roundNum,
    String throwerPlace,
    String throwerName,
    String throwerSide)
     {}
