package demoanalyzer.com.dem.parser.domain.model.raw;

import demoanalyzer.com.legacy.analyzer.GameplayEvent;

public record Rounds(
    long start,
    long freeze_end,
    long end,
    long official_end,
    int roundNum,
    String winner,
    String reason,
    double bombPlant,
    String bombSite)
    implements GameplayEvent {}
