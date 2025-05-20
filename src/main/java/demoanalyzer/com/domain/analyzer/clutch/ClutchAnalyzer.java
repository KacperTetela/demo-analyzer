package demoanalyzer.com.domain.analyzer.clutch;

import demoanalyzer.com.domain.analyzer.GameDetailsDTO;
import demoanalyzer.com.domain.replay.conversion.gameplay.KillsEvent;
import demoanalyzer.com.domain.replay.conversion.gameplay.RoundsEvent;

import java.util.*;

public class ClutchAnalyzer {

  private final GameDetailsDTO gameDetailsDTO;

  public ClutchAnalyzer(GameDetailsDTO gameDetails) {
    this.gameDetailsDTO = gameDetails;
  }

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
            clutcherName = getLastPlayerName(clutchForSide, round.roundNum(), victimsT);
          }
          if (victimsCT.size() == 4) {
            clutchForSide = "CT";
            amountOfEnemies = victimsT.size();
            clutcherName = getLastPlayerName(clutchForSide, round.roundNum(), victimsCT);
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

  private String getLastPlayerName(String side, int round, List<String> victimNames) {
    List<String> names = new ArrayList<>(gameDetailsDTO.getTeamForSide(side, round).namesOfPlayers());
    names.removeAll(victimNames);
    return names.get(0);
  }

  private boolean didTeamWinRound(RoundsEvent round, List<KillsEvent> killsEvent) {
    return false;
  }
}
