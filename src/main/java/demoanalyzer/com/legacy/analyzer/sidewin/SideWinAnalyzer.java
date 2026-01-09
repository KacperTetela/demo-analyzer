package demoanalyzer.com.legacy.analyzer.sidewin;

import demoanalyzer.com.legacy.analyzer.GameDetailsDTO;
import demoanalyzer.com.legacy.analyzer.Team;
import demoanalyzer.com.legacy.replay.conversion.raw.RoundsEvent;

import java.util.ArrayList;
import java.util.List;

public class SideWinAnalyzer {

  public List<TeamSideWins> analyzeTeamsSideWins(
      GameDetailsDTO basicReplayInfo, List<RoundsEvent> roundsEvents) {
    List<TeamSideWins> teamSideWins = new ArrayList<>();
    List<Team> teams = List.of(basicReplayInfo.teamA(), basicReplayInfo.teamB());
    for (Team team : teams) {
      int teamWinsT = getTeamWins(roundsEvents, basicReplayInfo, team, "t");
      int teamWinsCt = getTeamWins(roundsEvents, basicReplayInfo, team, "ct");
      teamSideWins.add(new TeamSideWins(team.name(), teamWinsT, teamWinsCt));
    }
    return teamSideWins;
  }

  private int getTeamWins(
      List<RoundsEvent> roundsEvents, GameDetailsDTO basicReplayInfo, Team team, String side) {
    int teamWins = 0;
    for (RoundsEvent roundsEvent : roundsEvents) {
      if (roundsEvent.winner().equalsIgnoreCase(side)) {
        Team currentTeam = basicReplayInfo.getTeamForSide(side, roundsEvent.roundNum());
        if (currentTeam.name().equalsIgnoreCase(team.name())) {
          teamWins++;
        }
      }
    }
    return teamWins;
  }
}
