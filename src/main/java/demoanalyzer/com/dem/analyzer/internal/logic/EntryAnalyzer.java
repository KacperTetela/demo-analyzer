package demoanalyzer.com.dem.analyzer.internal.logic;

import demoanalyzer.com.dem.analyzer.api.model.Entry;
import demoanalyzer.com.dem.parser.domain.model.raw.Kills;
import demoanalyzer.com.dem.parser.domain.model.raw.Rounds;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component // To jest teraz Bean Springowy (Logic Component)
public class EntryAnalyzer {

  public List<Entry> analyze(List<Kills> kills, List<Rounds> rounds) {
    List<Entry> entries = new ArrayList<>();

    // Sortowanie dla pewności
    List<Kills> sortedKills = new ArrayList<>(kills); // Kopia, żeby nie modyfikować oryginału
    sortedKills.sort(Comparator.comparingLong(Kills::tick));

    for (Rounds round : rounds) {
      Kills firstKill = findFirstKillInRound(sortedKills, round);

      if (firstKill != null) {
        boolean attackerWon = round.winner().equalsIgnoreCase(firstKill.attackerSide());

        entries.add(
            new Entry(
                round.roundNum(), firstKill.attackerName(), firstKill.attackerSide(), attackerWon));
      }
    }
    return entries;
  }

  private Kills findFirstKillInRound(List<Kills> kills, Rounds round) {
    return kills.stream()
        // Kills w zakresie czasowym rundy (zakładam, że ticki w Rounds są poprawne)
        // W Twoim parserze Rounds może nie mieć ticków start/end - sprawdź to!
        // Jeśli nie ma, musisz użyć logiki opartej na czasie freeze time lub tickach.
        // Zakładam, że Rounds ma pola startTick i endTick.
        // .filter(kill -> kill.tick() >= round.startTick() && kill.tick() <= round.endTick())
        // Jeśli Twoja klasa Rounds nie ma ticków, musisz je skorelować inaczej (np. Kills ma numer
        // rundy?)
        .filter(
            kill ->
                kill.roundNum()
                    == round.roundNum()) // To jest bezpieczniejsze jeśli masz roundNum w Kills
        .filter(kill -> !kill.isTeamKill()) // Zakładam, że masz metodę/logikę na teamkill
        .min(Comparator.comparingLong(Kills::tick))
        .orElse(null);
  }
}
