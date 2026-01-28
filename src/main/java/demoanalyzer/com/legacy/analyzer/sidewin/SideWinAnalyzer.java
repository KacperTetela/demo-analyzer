package demoanalyzer.com.legacy.analyzer.sidewin;

import demoanalyzer.com.legacy.analyzer.GameDetailsDTO;
import demoanalyzer.com.legacy.analyzer.Team;
import demoanalyzer.com.dem.parser.domain.model.raw.Rounds;

import java.util.ArrayList;
import java.util.List;

public class SideWinAnalyzer {

  public List<TeamSideWins> analyzeTeamsSideWins(
      GameDetailsDTO basicReplayInfo, List<Rounds> rounds) {
    List<TeamSideWins> teamSideWins = new ArrayList<>();
    List<Team> teams = List.of(basicReplayInfo.teamA(), basicReplayInfo.teamB());
    for (Team team : teams) {
      int teamWinsT = getTeamWins(rounds, basicReplayInfo, team, "t");
      int teamWinsCt = getTeamWins(rounds, basicReplayInfo, team, "ct");
      teamSideWins.add(new TeamSideWins(team.name(), teamWinsT, teamWinsCt));
    }
    return teamSideWins;
  }

  private int getTeamWins(
          List<Rounds> rounds, GameDetailsDTO basicReplayInfo, Team team, String side) {
    int teamWins = 0;
    for (Rounds rounds : rounds) {
      if (rounds.winner().equalsIgnoreCase(side)) {
        Team currentTeam = basicReplayInfo.getTeamForSide(side, rounds.roundNum());
        if (currentTeam.name().equalsIgnoreCase(team.name())) {
          teamWins++;
        }
      }
    }
    return teamWins;
  }
}
