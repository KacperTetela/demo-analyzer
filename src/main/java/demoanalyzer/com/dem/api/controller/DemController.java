package demoanalyzer.com.dem.api.controller;

import demoanalyzer.com.dem.api.dto.response.DemDetailsResponse;
import demoanalyzer.com.dem.api.dto.response.DemHandleResponse;
import demoanalyzer.com.dem.api.dto.response.DemHeaderResponse;
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
  public ResponseEntity<DemHandleResponse> upload(
      @RequestPart("file") MultipartFile uploadedFile, Authentication authentication) {

    return ResponseEntity.ok(
        DemHandleResponse.from(demService.handleDemFile(uploadedFile, getUserId(authentication))));
  }

  @GetMapping("/{demId}/status")
  public ResponseEntity<DemHandleResponse> checkDemStatus(
      @PathVariable Long demId, Authentication authentication) {

    return ResponseEntity.ok(
        DemHandleResponse.from(demService.getDem(demId, getUserId(authentication))));
  }

  @GetMapping("/{demId}/header")
  public ResponseEntity<DemHeaderResponse> checkDemHeader(
      @PathVariable Long demId, Authentication authentication) {

    return ResponseEntity.ok(
        DemHeaderResponse.from(demService.getDem(demId, getUserId(authentication))));
  }

  @GetMapping("/history")
  public ResponseEntity<List<DemHeaderResponse>> checkAllDemHeaders(Authentication authentication) {
    return ResponseEntity.ok(
        DemHeaderResponse.from(demService.getUserDemos(getUserId(authentication))));
  }

  @GetMapping("/{demId}/details")
  public ResponseEntity<DemDetailsResponse> getDemDetails(
      @PathVariable Long demId, Authentication authentication) {

    return ResponseEntity.ok(
        DemDetailsResponse.from(demService.getDem(demId, getUserId(authentication))));
  }

  private Long getUserId(Authentication authentication) {
    return Long.parseLong(authentication.getName());
  }
}
