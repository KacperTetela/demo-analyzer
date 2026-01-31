package demoanalyzer.com.dem.analyzer.internal.model;

public record MatchTeams(Team teamA, Team teamB) {

  public Team getTeamBySideAndRound(String side, int roundNum) {
    String sideOfTeamA = teamA.getSideForRound(roundNum);
    return sideOfTeamA.equalsIgnoreCase(side) ? teamA : teamB;
  }
}
