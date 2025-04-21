package demoanalyzer.com.domain.replay.conversion.gameplay;

public record KillsEvent(
    long tick,
    int roundNum,
    String attacker_name,
    String attacker_place,
    String attacker_side,
    String weapon,
    String victim_name,
    String victim_place) {}
