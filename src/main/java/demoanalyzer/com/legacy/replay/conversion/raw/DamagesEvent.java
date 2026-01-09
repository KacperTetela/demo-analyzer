package demoanalyzer.com.legacy.replay.conversion.raw;

public record DamagesEvent(
    long tick,
    int round_num,
    double armor,
    double attackerHealth,
    String attackerPlace,
    String attacker_name,
    int dmgArmor,
    int dmgHealth,
    int health,
    String hitGroup,
    String weapon)
    implements GameplayEvent {}
