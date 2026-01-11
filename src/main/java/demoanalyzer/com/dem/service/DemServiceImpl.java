package demoanalyzer.com.dem.service;

import demoanalyzer.com.dem.domain.service.DemService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DemServiceImpl implements DemService {
  @Override
  public Long handleDemFile(MultipartFile file) {
    return null;
  }
}
