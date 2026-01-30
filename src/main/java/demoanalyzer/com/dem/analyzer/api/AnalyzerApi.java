package demoanalyzer.com.dem.analyzer.api;

import demoanalyzer.com.dem.analyzer.api.dto.AnalysisResult;
import demoanalyzer.com.dem.parser.domain.model.CompleteMatchData;

public interface AnalyzerApi {
  AnalysisResult analyze(CompleteMatchData rawData);
}
