package demoanalyzer.com.dem.parser.domain.model.raw;


public record Damages(
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
     {}
