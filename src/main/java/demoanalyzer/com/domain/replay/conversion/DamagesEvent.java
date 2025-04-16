package demoanalyzer.com.domain.replay.conversion;

public record DamagesEvent(
    long tick,
    double armor,
    double attackerHealth,
    String attackerPlace,
    String attacker_name,
    int dmgArmor,
    int dmgHealth,
    int health,
    String hitGroup,
    String weapon,
    int round_num) {}
