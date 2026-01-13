package demoanalyzer.com.dem.api.controller;

import demoanalyzer.com.dem.api.dto.DemDetailsResponse;
import demoanalyzer.com.dem.api.dto.DemStatusResponse;
import demoanalyzer.com.dem.domain.service.DemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/dem")
@RequiredArgsConstructor
public class DemController {

  private final DemService demService;

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<DemStatusResponse> upload(@RequestPart("file") MultipartFile uploadedFile) {
    return ResponseEntity.ok(DemStatusResponse.from(demService.handleDemFile(uploadedFile)));
  }

  @GetMapping("/{demId}/status")
  public ResponseEntity<DemStatusResponse> checkStatus(@PathVariable Long demId) {
    return ResponseEntity.ok(DemStatusResponse.from(demService.getDemStatus(demId)));
  }

  @GetMapping("/{demId}/download")
  public ResponseEntity<DemDetailsResponse> download(@PathVariable Long demId) {
    return ResponseEntity.ok(DemDetailsResponse.from(demService.getDemDetails(demId)));
  }
}
