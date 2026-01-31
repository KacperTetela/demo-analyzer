package demoanalyzer.com.dem.parser.domain.model.raw;


public record Footsteps(
    long tick, int roundNum, String playerPlace, String playerName, String playerSide)
     {}
