package demoanalyzer.com.dem.analyzer.internal.logic;

import demoanalyzer.com.dem.analyzer.api.dto.Clutch;
import demoanalyzer.com.dem.analyzer.internal.model.MatchTeams;
import demoanalyzer.com.dem.parser.domain.model.raw.Kills;
import demoanalyzer.com.dem.parser.domain.model.raw.Rounds;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ClutchAnalyzer {

  public List<Clutch> analyze(List<Kills> kills, List<Rounds> rounds, MatchTeams teams) {
    List<Clutch> clutches = new ArrayList<>();

    for (Rounds round : rounds) {
      String clutchForSide = "";
      String clutcherName = "";
      int amountOfEnemies = 0;

      List<String> victimsT = new ArrayList<>();
      List<String> victimsCT = new ArrayList<>();

      // Sortujemy kille w rundzie
      List<Kills> roundKills =
          kills.stream()
              .filter(k -> k.roundNum() == round.roundNum())
              .sorted(Comparator.comparingLong(Kills::tick))
              .toList();

      for (Kills kill : roundKills) {
        if ("CT".equalsIgnoreCase(kill.victimSide())) victimsCT.add(kill.victimName());
        else victimsT.add(kill.victimName());

        // Sprawdzamy moment 1vsX
        if (clutchForSide.isEmpty()) {
          // Jeśli zginęło 4 Terrorystów -> został 1 T -> Clutch dla T przeciwko żywym CT
          if (victimsT.size() == 4) {
            clutchForSide = "T";
            // Liczba wrogów = 5 (zakładam 5vs5) minus liczba zabitych CT do tej pory
            amountOfEnemies = 5 - victimsCT.size();
            clutcherName = getLastPlayerName(clutchForSide, round.roundNum(), victimsT, teams);
          }
          // Jeśli zginęło 4 CT -> został 1 CT -> Clutch dla CT
          if (victimsCT.size() == 4) {
            clutchForSide = "CT";
            amountOfEnemies = 5 - victimsT.size();
            clutcherName = getLastPlayerName(clutchForSide, round.roundNum(), victimsCT, teams);
          }
        }
      }

      // Jeśli wygrała strona, która miała clutchera -> sukces
      if (!clutchForSide.isEmpty() && clutchForSide.equalsIgnoreCase(round.winner())) {
        clutches.add(new Clutch(round.roundNum(), clutcherName, amountOfEnemies));
      }
    }
    return clutches;
  }

  private String getLastPlayerName(
      String side, int roundNum, List<String> deadPlayers, MatchTeams teams) {
    // Pobieramy pełny skład danej strony w tej rundzie
    List<String> allPlayers =
        new ArrayList<>(teams.getTeamBySideAndRound(side, roundNum).playerNames());

    // Usuwamy martwych
    allPlayers.removeAll(deadPlayers);

    // Zwracamy ostatniego żywego (powinien być jeden)
    return allPlayers.isEmpty() ? "Unknown" : allPlayers.get(0);
  }
}
