package demoanalyzer.com.dem.api.controller;

import demoanalyzer.com.dem.api.dto.response.DemDetailsResponse;
import demoanalyzer.com.dem.api.dto.response.DemStatusResponse;
import demoanalyzer.com.dem.domain.service.DemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/dem")
@RequiredArgsConstructor
public class DemController {

  private final DemService demService;

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<DemStatusResponse> upload(
      @RequestPart("file") MultipartFile uploadedFile, Authentication authentication) {

    return ResponseEntity.ok(
        DemStatusResponse.from(
            demService.handleDemFile(uploadedFile, Long.parseLong(authentication.getName()))));
  }

  @GetMapping("/history")
  public ResponseEntity<List<DemStatusResponse>> checkAllAvailableDems(
      Authentication authentication) {
    return ResponseEntity.ok(
        DemStatusResponse.from(
            demService.getAllDemsStatuses(Long.parseLong(authentication.getName()))));
  }

  @GetMapping("/{demId}/status")
  public ResponseEntity<DemStatusResponse> checkStatus(
      @PathVariable Long demId, Authentication authentication) {
    return ResponseEntity.ok(
        DemStatusResponse.from(
            demService.getDemStatus(demId, Long.parseLong(authentication.getName()))));
  }

  @GetMapping("/{demId}/download")
  public ResponseEntity<DemDetailsResponse> download(
      @PathVariable Long demId, Authentication authentication) {
    return ResponseEntity.ok(
        DemDetailsResponse.from(
            demService.getDemDetails(demId, Long.parseLong(authentication.getName()))));
  }
}
