package demoanalyzer.com.domain.analyzer.entry;

import demoanalyzer.com.domain.replay.conversion.gameplay.KillsEvent;
import demoanalyzer.com.domain.replay.conversion.gameplay.RoundsEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EntryAnalyzer {

  public List<EntryDTO> analyzeEntryFrags(
      List<KillsEvent> killsEvents, List<RoundsEvent> roundsEvents) {
    List<EntryDTO> entries = new ArrayList<>();

    // Sortuj eventy killów według ticku (czas)
    killsEvents.sort(Comparator.comparingLong(KillsEvent::tick));

    // Dla każdej rundy znajdź pierwszy kill
    for (RoundsEvent round : roundsEvents) {
      KillsEvent firstKill = findFirstKillInRound(killsEvents, round);

      if (firstKill != null) {
        EntryDTO entry = createEntryDTO(firstKill, round);
        entries.add(entry);
      }
    }

    return entries;
  }

  private KillsEvent findFirstKillInRound(List<KillsEvent> kills, RoundsEvent round) {
    return kills.stream()
        .filter(kill -> kill.tick() >= round.start() && kill.tick() <= round.end())
        .filter(kill -> !kill.isTeamKill())
        .min(Comparator.comparingLong(KillsEvent::tick))
        .orElse(null);
  }

  private EntryDTO createEntryDTO(KillsEvent kill, RoundsEvent round) {
    double timeFromRoundStart = (kill.tick() - round.start()) / 128.0; // 128 ticków = 1 sekunda

    return new EntryDTO(round.roundNum(), kill.attackerName(), true);
  }

  /*  private boolean didTeamWinRound(EntryDTO entry, List<RoundsEvent> rounds) {
    return rounds.stream()
        .filter(round -> round.roundNumber() == entry.round())
        .anyMatch(round -> round.winnerSide().equals(entry.side()));
  }*/
}
