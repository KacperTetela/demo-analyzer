package demoanalyzer.com.domain.analyzer;

import java.util.List;

public record Team(String startingSide, List<String> namesOfPlayers, String name) {
  public String getSideInfo(int roundNum) {
    if (roundNum > 24) {
      int currentRoundNum = -24;
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
