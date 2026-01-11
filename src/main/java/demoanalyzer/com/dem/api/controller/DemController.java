package demoanalyzer.com.dem.api.controller;

import demoanalyzer.com.dem.api.dto.DemStatusResponse;
import demoanalyzer.com.dem.domain.model.status.DemAnalysisStatus;
import demoanalyzer.com.dem.domain.service.DemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@RestController
@RequestMapping("/api/dem")
@RequiredArgsConstructor
public class DemController {

  private final DemService demService;

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<DemStatusResponse> upload(@RequestPart("file") MultipartFile file) {

    return ResponseEntity.ok(
        new DemStatusResponse(1, Instant.now(), DemAnalysisStatus.PENDING, ""));
  }

  @GetMapping
  public ResponseEntity<DemStatusResponse> checkStatus(@PathVariable Long demId) {

    return ResponseEntity.ok(
        new DemStatusResponse(1, Instant.now(), DemAnalysisStatus.PENDING, ""));
  }
}
