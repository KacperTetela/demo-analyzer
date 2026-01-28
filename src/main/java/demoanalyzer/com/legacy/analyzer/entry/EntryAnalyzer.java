package demoanalyzer.com.legacy.analyzer.entry;

import demoanalyzer.com.dem.parser.domain.model.raw.Kills;
import demoanalyzer.com.dem.parser.domain.model.raw.Rounds;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EntryAnalyzer {

  public List<EntryDTO> analyzeEntryFrags(
          List<Kills> kills, List<Rounds> rounds) {
    List<EntryDTO> entries = new ArrayList<>();

    // Sortuj eventy killów według ticku (czas)
    kills.sort(Comparator.comparingLong(Kills::tick));

    // Dla każdej rundy znajdź pierwszy kill
    for (Rounds round : rounds) {
      Kills firstKill = findFirstKillInRound(kills, round);
      if (firstKill != null) {
        EntryDTO entry =
            new EntryDTO(
                round.roundNum(), firstKill.attackerName(), didTeamWinRound(round, firstKill));
        entries.add(entry);
      }
    }

    return entries;
  }

  private Kills findFirstKillInRound(List<Kills> kills, Rounds round) {
    return kills.stream()
        .filter(kill -> kill.tick() >= round.start() && kill.tick() <= round.end())
        .filter(kill -> !kill.isTeamKill())
        .min(Comparator.comparingLong(Kills::tick))
        .orElse(null);
  }

  private boolean didTeamWinRound(Rounds round, Kills kills) {
    if (round.roundNum() == kills.roundNum()) {
      return round.winner().equals(kills.attackerSide());
    }
    return false;
  }
}
