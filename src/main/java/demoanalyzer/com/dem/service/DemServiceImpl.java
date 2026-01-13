package demoanalyzer.com.dem.service;

import demoanalyzer.com.dem.domain.model.Dem;
import demoanalyzer.com.dem.domain.model.metadata.Metadata;
import demoanalyzer.com.dem.domain.service.DemService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DemServiceImpl implements DemService {
  @Override
  public Metadata handleDemFile(MultipartFile file) {
    return new Metadata(Long.valueOf(1337));
  }

  @Override
  public Metadata getDemStatus(Long demId) {
    return new Metadata(Long.valueOf(1337));
  }

  @Override
  public Dem getDemDetails(Long demId) {
    return new Dem(demId);
  }
}
