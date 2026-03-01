package demoanalyzer.com.dem.analyzer.internal.logic;

import demoanalyzer.com.dem.analyzer.internal.model.MatchTeams;
import demoanalyzer.com.dem.analyzer.internal.model.Team;
import demoanalyzer.com.dem.parser.domain.model.raw.Kills;
import demoanalyzer.com.dem.parser.domain.model.raw.Rounds;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TeamSideCalculator {

  // Zmieniono List<Round> na List<Rounds> zgodnie z Twoim rekordem
  public MatchTeams calculateTeams(List<Kills> kills, List<Rounds> rounds) {

    // 1. Zbieramy unikalnych graczy (bez zmian)
    Set<String> startingCT = new HashSet<>();
    Set<String> startingT = new HashSet<>();

    for (Kills kill : kills) {
      if (kill.roundNum() <= 12) {
        if ("CT".equalsIgnoreCase(kill.attackerSide())) startingCT.add(kill.attackerName());
        else if ("T".equalsIgnoreCase(kill.attackerSide())) startingT.add(kill.attackerName());

        if ("CT".equalsIgnoreCase(kill.victimSide())) startingCT.add(kill.victimName());
        else if ("T".equalsIgnoreCase(kill.victimSide())) startingT.add(kill.victimName());
      }
    }

    // 2. Liczymy wynik
    int scoreA = 0;
    int scoreB = 0;

    // Team A zaczynamy traktować jako CT (tak ustaliliśmy przy tworzeniu setów)
    String teamAStartingSide = "CT";

    for (Rounds round : rounds) {
      // Używamy pola winner() z Twojego rekordu
      String rawWinner = round.winner();

      // Normalizujemy nazwę (bo parser może zwracać "Terrorist" zamiast "T")
      String winnerSide = normalizeWinnerSide(rawWinner);

      if (winnerSide == null) continue; // Pomijamy błędy lub remisy

      // Sprawdzamy, gdzie był Team A w tej rundzie
      /*
      sdsdsdsd
       */

      String currentSideOfTeamA = getSideForRound(teamAStartingSide, round.roundNum());

      if (currentSideOfTeamA.equalsIgnoreCase(winnerSide)) {
        scoreA++;
      } else {
        scoreB++;
      }
    }

    // 3. Tworzymy Teamy z wpisanym wynikiem
    Team teamA = new Team("CT", new ArrayList<>(startingCT), "Team CT (Start)", scoreA);
    Team teamB = new Team("T", new ArrayList<>(startingT), "Team T (Start)", scoreB);

    return new MatchTeams(teamA, teamB);
  }

  // --- Metody pomocnicze ---

  // Zamienia to co wypluwa parser na "CT" lub "T"
  private String normalizeWinnerSide(String winner) {
    if (winner == null) return null;
    String w = winner.toUpperCase();

    // Jeśli zaczyna się na "C" (np. "CT", "Counter-Terrorist") -> CT
    if (w.startsWith("C")) return "CT";
    // Jeśli zaczyna się na "T" (np. "T", "Terrorist") -> T
    if (w.startsWith("T")) return "T";

    return null;
  }

  private String getSideForRound(String startingSide, int roundNum) {
    // Logika MR12 + Dogrywki
    if (roundNum > 24) {
      if ((roundNum - 24) <= 3) return flip(startingSide);
      else return startingSide;
    } else {
      if (roundNum > 12) return flip(startingSide);
      else return startingSide;
    }
  }

  private String flip(String side) {
    return "T".equalsIgnoreCase(side) ? "CT" : "T";
  }
}
