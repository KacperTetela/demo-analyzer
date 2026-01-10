package demoanalyzer.com.dem.domain.service;

import org.springframework.web.multipart.MultipartFile;

public interface DemService {
  void handleDemFile(MultipartFile file);
}
