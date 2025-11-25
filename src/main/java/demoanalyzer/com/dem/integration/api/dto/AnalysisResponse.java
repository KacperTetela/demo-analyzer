package demoanalyzer.com.dem.integration.api.dto;

import demoanalyzer.com.dem.integration.domain.model.AnalysisResult;

public record AnalysisResponse(long matchId) {

  public static AnalysisResponse from(AnalysisResult result) {
    return new AnalysisResponse(result.getMatchId());
  }
}
