package demoanalyzer.com.dem.core.domain.service;

import demoanalyzer.com.dem.core.domain.model.Dem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DemService {
  Dem handleDemFile(MultipartFile file, Long ownerId);

  Dem getDem(Long demId, Long ownerId);

  List<Dem> getUserDemos(Long ownerId);
}
