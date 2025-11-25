package demoanalyzer.com.dem.integration.service;

import demoanalyzer.com.dem.integration.domain.model.AnalysisResult;
import demoanalyzer.com.dem.integration.domain.service.IntegrationService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class IntegrationServiceImpl implements IntegrationService {
    @Override
    public AnalysisResult handleDemFile(MultipartFile file) {
    return null;
    }
}
