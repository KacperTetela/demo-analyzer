package demoanalyzer.com.dem.domain.service;

import demoanalyzer.com.dem.domain.model.Dem;
import demoanalyzer.com.dem.domain.model.metadata.Metadata;
import org.springframework.web.multipart.MultipartFile;

public interface DemService {
  Metadata handleDemFile(MultipartFile file, Long ownerId);

  Metadata getDemStatus(Long demId, Long ownerId);

  Dem getDemDetails(Long demId, Long ownerId);
}
