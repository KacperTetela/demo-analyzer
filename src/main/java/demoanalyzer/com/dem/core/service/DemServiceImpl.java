package demoanalyzer.com.dem.core.service;

import demoanalyzer.com.dem.core.domain.exception.DemNotFoundException;
import demoanalyzer.com.dem.core.domain.exception.UnauthorizedDemAccessException;
import demoanalyzer.com.dem.core.domain.model.Dem;
import demoanalyzer.com.dem.core.domain.model.metadata.Metadata;
import demoanalyzer.com.dem.core.domain.repository.DemRepository;
import demoanalyzer.com.dem.core.domain.service.DemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DemServiceImpl implements DemService {

  private final DemRepository demRepository;
  private final DemProcessingService demProcessingService;

  @Override
  @Transactional // Transakcja tylko dla zapisu początkowego stanu
  public Dem handleDemFile(MultipartFile multipartFile, Long ownerId) {
    log.info(
        "Request received. Creating DB entry for file: {}", multipartFile.getOriginalFilename());

    // 1. Zapisz wstępny rekord w bazie (Status: PENDING/PROCESSING)
    Dem dem =
        Dem.builder()
            .metadata(
                new Metadata(ownerId)) // Upewnij się, że domyślny status w Metadata to PENDING
            .build();

    Dem savedDem = demRepository.save(dem);

    // 2. Zapisz MultipartFile do pliku tymczasowego na dysku
    // Jest to konieczne, bo MultipartFile "ginie" po zakończeniu tego requestu
    try {
      File tempFile = createTempFile(multipartFile);

      // 3. Zleć przetwarzanie w tle (metoda @Async)
      // Przekazujemy ID rekordu i fizyczny plik
      demProcessingService.processDemo(savedDem.getId(), tempFile);

    } catch (IOException e) {
      log.error("Failed to save temporary file", e);
      throw new RuntimeException("Internal storage error", e);
    }

    log.info("Immediate response: Returning ID {} to client.", savedDem.getId());

    // 4. Zwróć obiekt z ID natychmiast do kontrolera
    return savedDem;
  }

  // Helper do zapisu pliku tymczasowego
  private File createTempFile(MultipartFile multipartFile) throws IOException {
    String originalName = multipartFile.getOriginalFilename();
    String extension =
        originalName != null && originalName.contains(".")
            ? originalName.substring(originalName.lastIndexOf("."))
            : ".dem";

    // Tworzy plik w katalogu tymczasowym systemu (/tmp lub C:\Users\...\AppData\Local\Temp)
    Path tempPath = Files.createTempFile("dem_upload_" + UUID.randomUUID(), extension);
    File tempFile = tempPath.toFile();

    multipartFile.transferTo(tempFile);
    return tempFile;
  }

  @Override
  @Transactional(readOnly = true)
  public Dem getDem(Long demId, Long ownerId) {
    Dem dem = demRepository.findById(demId).orElseThrow(() -> new DemNotFoundException(demId));
    validateOwnership(dem, ownerId);
    return dem;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Dem> getUserDemos(Long ownerId) {
    return demRepository.findAll(ownerId);
  }

  private void validateOwnership(Dem dem, Long currentUserId) {
    Long demoOwnerId = dem.getMetadata().getOwnerId();
    if (!demoOwnerId.equals(currentUserId)) {
      throw new UnauthorizedDemAccessException();
    }
  }
}
