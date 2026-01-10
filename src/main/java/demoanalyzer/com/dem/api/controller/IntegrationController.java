package demoanalyzer.com.dem.api.controller;

import demoanalyzer.com.dem.api.dto.AnalysisResponse;
import demoanalyzer.com.dem.domain.service.DemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/dem")
@RequiredArgsConstructor
public class IntegrationController {

  private final DemService demService;

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<AnalysisResponse> uploadDem(@RequestPart("file") MultipartFile file) {

    return ResponseEntity.ok(AnalysisResponse.from(null));
  }
}
