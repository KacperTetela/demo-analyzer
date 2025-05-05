package demoanalyzer.com.domain.analyzer.clutch;

import demoanalyzer.com.domain.replay.conversion.gameplay.KillsEvent;
import demoanalyzer.com.domain.replay.conversion.gameplay.RoundsEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClutchAnalyzer {

  public List<ClutchDTO> analyzeClutch(
      List<KillsEvent> killsEvents, List<RoundsEvent> roundsEvents) {

    // Inicjalizacja listy wynikowej
    List<ClutchDTO> clutches = new ArrayList<>();


    // Iteracja po rundach
    for (RoundsEvent round : roundsEvents) {
      // boolean clutchAttempt = false;
      String clutchForSide = "";
      String clutcherName = "";
      int amountOfEnemies = 0;
      // liczba żywych graczy
      int victimT = 5;
      int victimCT = 5;

      for (KillsEvent kill :
          killsEvents.stream()
              .filter(killsEvent -> killsEvent.roundNum() == round.roundNum())
              .sorted(Comparator.comparing(KillsEvent::tick))
              .toList()) {
        if (kill.victimSide().equals("CT")) victimCT--;
        else victimT--;

        if (clutchForSide.equals("")) {
          if (victimT == 1) {
            clutchForSide = "T";
            amountOfEnemies = victimCT;
            clutcherName = kill.attackerName();
          }
          if (victimCT == 1) {
            clutchForSide = "CT";
            amountOfEnemies = victimT;
            clutcherName = kill.attackerName();
          }
        }
      }

      if (clutchForSide.equals(round.winner().toString())) {
        clutches.add(new ClutchDTO(round.roundNum(), clutcherName, amountOfEnemies));
      }
    }

    // szukamy druzyny w ktorej pierwsza duzryna zostalo 4 zabite + wyliczamy iles graczy bylo w
    // enemy teamie w
    // przypadku zastnienia takiej sytuacji.

    return clutches;
  }

  private String getLastPlayerName(String side, List<KillsEvent> kills) {
    return null;
  }

  private boolean didTeamWinRound(RoundsEvent round, List<KillsEvent> killsEvent) {
    return false;
  }
}
