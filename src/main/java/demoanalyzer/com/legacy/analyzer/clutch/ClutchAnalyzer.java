package demoanalyzer.com.legacy.analyzer.clutch;

import demoanalyzer.com.legacy.analyzer.GameDetailsDTO;
import demoanalyzer.com.legacy.replay.conversion.gameplay.KillsEvent;
import demoanalyzer.com.legacy.replay.conversion.gameplay.RoundsEvent;

import java.util.*;

public class ClutchAnalyzer {


  public List<ClutchDTO> analyzeClutch(
      List<KillsEvent> killsEvents, List<RoundsEvent> roundsEvents,GameDetailsDTO gameDetails
  ) {

    // Inicjalizacja listy wynikowej
    List<ClutchDTO> clutches = new ArrayList<>();

    // Iteracja po rundach
    for (RoundsEvent round : roundsEvents) {
      // boolean clutchAttempt = false;
      String clutchForSide = "";
      String clutcherName = "";
      int amountOfEnemies = 0;
      // liczba żywych graczy
      List<String> victimsT = new ArrayList<>();
      List<String> victimsCT = new ArrayList<>();

      for (KillsEvent kill :
          killsEvents.stream()
              .filter(killsEvent -> killsEvent.roundNum() == round.roundNum())
              .sorted(Comparator.comparing(KillsEvent::tick))
              .toList()) {
        if (kill.victimSide().equals("CT")) victimsCT.add(kill.victimName());
        else victimsT.add(kill.victimName());

        if (clutchForSide.isEmpty()) {
          if (victimsT.size() == 4) {
            clutchForSide = "T";
            amountOfEnemies = victimsCT.size();
            clutcherName = getLastPlayerName(clutchForSide, round.roundNum(), victimsT, gameDetails);
          }
          if (victimsCT.size() == 4) {
            clutchForSide = "CT";
            amountOfEnemies = victimsT.size();
            clutcherName = getLastPlayerName(clutchForSide, round.roundNum(), victimsCT, gameDetails);
          }
        }
      }

      if (clutchForSide.equalsIgnoreCase(round.winner().toString())) {
        clutches.add(new ClutchDTO(round.roundNum(), clutcherName, amountOfEnemies));
      }
    }

    // szukamy druzyny w ktorej pierwsza duzryna zostalo 4 zabite + wyliczamy iles graczy bylo w
    // enemy teamie w
    // przypadku zastnienia takiej sytuacji.

    return clutches;
  }

  private String getLastPlayerName(String side, int round, List<String> victimNames,GameDetailsDTO gameDetails) {
    List<String> names = new ArrayList<>(gameDetails.getTeamForSide(side, round).namesOfPlayers());
    names.removeAll(victimNames);
    return names.get(0);
  }

  private boolean didTeamWinRound(RoundsEvent round, List<KillsEvent> killsEvent) {
    return false;
  }
}
