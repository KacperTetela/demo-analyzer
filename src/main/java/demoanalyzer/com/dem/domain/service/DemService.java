package demoanalyzer.com.dem.domain.service;

import org.springframework.web.multipart.MultipartFile;

public interface DemService {
  Long handleDemFile(MultipartFile file);
}
