package demoanalyzer.com.domain.analyzer.sidewin;

import demoanalyzer.com.domain.analyzer.GameDetailsDTO;
import demoanalyzer.com.domain.analyzer.Team;
import demoanalyzer.com.domain.replay.conversion.gameplay.RoundsEvent;

import java.util.ArrayList;
import java.util.List;

public class SideWinAnalyzer {

  public List<TeamSideWinsDTO> analyzeTeamsSideWins(
      GameDetailsDTO basicReplayInfo, List<RoundsEvent> roundsEvents) {
    List<TeamSideWinsDTO> teamSideWins = new ArrayList<>();
    List<Team> teams = List.of(basicReplayInfo.teamA(), basicReplayInfo.teamB());
    for (Team team : teams) {
      int teamWinsT = getTeamWins(roundsEvents, basicReplayInfo, team, "t");
      int teamWinsCt = getTeamWins(roundsEvents, basicReplayInfo, team, "ct");
      teamSideWins.add(new TeamSideWinsDTO(team.name(), teamWinsT, teamWinsCt));
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
