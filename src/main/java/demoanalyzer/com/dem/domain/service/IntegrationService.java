package demoanalyzer.com.dem.domain.service;

import demoanalyzer.com.dem.integration.domain.model.AnalysisResult;
import org.springframework.web.multipart.MultipartFile;

public interface IntegrationService {
  void handleDemFile(MultipartFile file);
}
