package demoanalyzer.com.dem.integration.domain.model;

public class AnalysisResult {
  private final long matchId;

  public AnalysisResult(long matchId) {
    this.matchId = matchId;
  }

  public long getMatchId() {
    return matchId;
  }
}
