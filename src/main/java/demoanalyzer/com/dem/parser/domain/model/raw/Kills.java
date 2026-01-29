package demoanalyzer.com.dem.parser.domain.model.raw;

import demoanalyzer.com.legacy.analyzer.GameplayEvent;

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
    implements GameplayEvent {

  public boolean isTeamKill() {
    if (attackerSide == null || victimSide == null) return false;
    return attackerSide.equals(victimSide.toString());
  }
}
