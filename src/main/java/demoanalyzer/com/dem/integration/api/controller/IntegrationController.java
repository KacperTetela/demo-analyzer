package demoanalyzer.com.dem.integration.api.controller;

import demoanalyzer.com.dem.integration.api.dto.AnalysisResponse;
import demoanalyzer.com.dem.integration.domain.model.AnalysisResult;
import demoanalyzer.com.dem.integration.domain.service.IntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/dem")
@RequiredArgsConstructor
public class IntegrationController {

  private final IntegrationService integrationService;

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<AnalysisResponse> uploadDem(@RequestPart("file") MultipartFile file) {

    AnalysisResult result = integrationService.handleDemFile(file);

    return ResponseEntity.ok(AnalysisResponse.from(result));
  }
}
