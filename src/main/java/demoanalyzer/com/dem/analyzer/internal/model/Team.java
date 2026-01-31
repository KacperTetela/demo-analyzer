package demoanalyzer.com.dem.analyzer.internal.model;

import java.util.List;

public record Team(String startingSide, List<String> namesOfPlayers, String name) {

  public String getSideForRound(int roundNum) {
    // CS2 MR12 (zmiana stron po 12 rundach)
    // Dogrywki są bardziej skomplikowane, ale przenieśmy logikę 1:1 z legacy na start
    if (roundNum > 24) {
      // Logika dogrywki z legacy
      int currentRoundNum =
          roundNum - 24; // to wymaga dopracowania w przyszłości, ale zostawmy jak było
      if (currentRoundNum <= 3) return getOppositeSide();
      else return startingSide;
    } else {
      if (roundNum > 12) return getOppositeSide();
      else return startingSide;
    }
  }

  private String getOppositeSide() {
    return startingSide.equals("T") ? "CT" : "T";
  }
}
