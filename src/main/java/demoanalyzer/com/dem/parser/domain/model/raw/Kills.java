package demoanalyzer.com.dem.parser.domain.model.raw;


public record Kills(
    long tick,
    int roundNum,
    String attackerName,
    String attackerPlace,
    String attackerSide,
    String weapon,
    String victimName,
    String victimPlace,
    String victimSide)
     {

  public boolean isTeamKill() {
    if (attackerSide == null || victimSide == null) return false;
    return attackerSide.equals(victimSide.toString());
  }
}
