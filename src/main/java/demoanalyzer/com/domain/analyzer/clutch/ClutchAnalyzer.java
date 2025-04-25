package demoanalyzer.com.domain.analyzer.clutch;

import demoanalyzer.com.domain.replay.conversion.gameplay.KillsEvent;
import demoanalyzer.com.domain.replay.conversion.gameplay.RoundsEvent;

import java.util.ArrayList;
import java.util.List;

public class ClutchAnalyzer {

  public List<ClutchDTO> analyzeClutch(
      List<KillsEvent> killsEvents, List<RoundsEvent> roundsEvents) {

    List<ClutchDTO> clutches = new ArrayList<>();

    // Czy sytuacja byla clutchem
    for (RoundsEvent round : roundsEvents) {
      String clutchForSide = "";
      //boolean clutchAttempt = false;
      for (KillsEvent kill : killsEvents) {
        int victimT = 5;
        int victimCT = 5;
        int amountOfEnemies;
        if (kill.victimSide().equals("CT")) victimCT--;
        else victimT--;
        if (victimT == 1) {
          clutchForSide = "T";
          amountOfEnemies = victimCT;
          kill.victimName();
          break;
        }
        if (victimCT == 1) {
          clutchForSide = "CT";
          amountOfEnemies = victimT;
          kill.victimName();
          break;
        }
      }
      if (clutchForSide.equals(round.winner())) {
        /*ClutchDTO clutch = new ClutchDTO(round.roundNum(), )*/
      }


    }

    // szukamy druzyny w ktorej pierwsza duzryna zostalo 4 zabite + wyliczamy iles graczy bylo w
    // enemy teamie w
    // przypadku zastnienia takiej sytuacji.

    return null;
  }

  private String getLastPlayerName(String side, List<KillsEvent> kills) {
    return null;
  }

  private boolean didTeamWinRound(RoundsEvent round, List<KillsEvent> killsEvent) {
    return false;
  }
}
