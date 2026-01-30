package demoanalyzer.com.dem.analyzer.internal.logic;

import demoanalyzer.com.dem.analyzer.internal.model.team.MatchTeams;
import demoanalyzer.com.dem.analyzer.internal.model.team.Team;
import demoanalyzer.com.dem.parser.domain.model.raw.Rounds;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SideWinCalculator {

  // Używamy rekordu wewnętrznego lub DTO z API, tutaj zwracam prostą klasę/rekord
  public record TeamSideWins(String teamName, int ctWins, int tWins) {}

  public List<TeamSideWins> analyze(MatchTeams teams, List<Rounds> rounds) {
    List<TeamSideWins> results = new ArrayList<>();

    List<Team> teamList = List.of(teams.teamA(), teams.teamB());

    for (Team team : teamList) {
      int winsAsT = countWinsForSide(rounds, teams, team, "T");
      int winsAsCt = countWinsForSide(rounds, teams, team, "CT");
      results.add(new TeamSideWins(team.name(), winsAsCt, winsAsT));
    }
    return results;
  }

  private int countWinsForSide(
      List<Rounds> rounds, MatchTeams teams, Team targetTeam, String sideToCheck) {
    int wins = 0;
    for (Rounds round : rounds) {
      // Czy runda została wygrana przez sprawdzaną stronę (np. CT)?
      if (round.winner().equalsIgnoreCase(sideToCheck)) {
        // Kto grał po tej stronie w tej rundzie?
        Team teamOnSide = teams.getTeamBySideAndRound(sideToCheck, round.roundNum());

        // Czy to ta drużyna, którą liczymy?
        if (teamOnSide.name().equalsIgnoreCase(targetTeam.name())) {
          wins++;
        }
      }
    }
    return wins;
  }
}
