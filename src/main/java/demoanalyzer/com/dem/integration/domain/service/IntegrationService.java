package demoanalyzer.com.dem.integration.domain.service;

import demoanalyzer.com.dem.integration.domain.model.AnalysisResult;
import org.springframework.web.multipart.MultipartFile;

public interface IntegrationService {
  AnalysisResult handleDemFile(MultipartFile file);
}
