package demoanalyzer.com.legacy.analyzer.sidewin;

public class TeamSideWins {
  private String teamName;
  private int ctWins;
  private int tWins;
  private double percentageCtWin;
  private double percentageTWin;

  public TeamSideWins(String teamName, int ctWins, int tWins) {
    this.teamName = teamName;
    this.ctWins = ctWins;
    this.tWins = tWins;
  }

  private double getCtWinPercentage() {
    return (ctWins / (ctWins + tWins)) * 100;
  }

  private double getTWinPercentage() {
    return (tWins / (ctWins + tWins)) * 100;
  }

  public TeamSideWinsDTO exportToDTO() {
    return new TeamSideWinsDTO(teamName, ctWins, tWins, getCtWinPercentage(), getTWinPercentage());
  }

  private record TeamSideWinsDTO(
      String teamName, int ctWins, int tWins, double percentageCtWin, double percentageTWin) {}
}