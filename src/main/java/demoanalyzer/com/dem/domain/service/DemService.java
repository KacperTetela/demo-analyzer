package demoanalyzer.com.dem.domain.service;

import demoanalyzer.com.dem.domain.model.Dem;
import demoanalyzer.com.dem.domain.model.metadata.Metadata;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DemService {
  Dem handleDemFile(MultipartFile file, Long ownerId);

  Dem getDemStatus(Long demId, Long ownerId);

  List<Dem> getAllDemsStatuses(Long ownerId);

  Dem getDemDetails(Long demId, Long ownerId);
}
