package demoanalyzer.com.dem.domain.service;

import demoanalyzer.com.dem.domain.model.Dem;
import demoanalyzer.com.dem.domain.model.metadata.Metadata;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DemService {
  Dem handleDemFile(MultipartFile file, Long ownerId);

  Dem getDem(Long demId, Long ownerId);

  List<Dem> getUserDemos(Long ownerId);
}
