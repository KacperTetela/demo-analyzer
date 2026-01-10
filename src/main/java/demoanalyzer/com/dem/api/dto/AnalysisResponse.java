package demoanalyzer.com.dem.api.dto;

public record AnalysisResponse(long matchId) {

  public static AnalysisResponse from(AnalysisResult result) {
    return new AnalysisResponse(result.getMatchId());
  }
}
