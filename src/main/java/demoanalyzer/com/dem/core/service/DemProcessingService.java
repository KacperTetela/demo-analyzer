package demoanalyzer.com.dem.core.service;

import demoanalyzer.com.dem.core.domain.model.Dem;
import demoanalyzer.com.dem.core.domain.model.header.Header;
import demoanalyzer.com.dem.core.domain.repository.DemRepository;
import demoanalyzer.com.dem.parser.domain.model.CompleteMatchData;
import demoanalyzer.com.dem.parser.domain.service.ParserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class DemProcessingService {

  private final DemRepository demRepository;
  private final ParserService parserService;

  @Async // Ta metoda wykona się w osobnym wątku
  @Transactional
  public void processDemo(Long demId, File tempFile) {
    log.info("BACKGROUND: Starting processing for demo ID: {}", demId);

    // Znajdź encję, która została utworzona wcześniej
    Dem dem = demRepository.findById(demId)
            .orElseThrow(() -> new IllegalStateException("Dem not found: " + demId));

    try {
      // WAŻNE: Parser musi umieć obsłużyć java.io.File, a nie tylko MultipartFile.
      // Jeśli twój parser wymaga MultipartFile, musisz go lekko przerobić,
      // żeby przyjmował File lub InputStream. Zakładam tutaj, że parserService.parse(File) jest możliwe.
      CompleteMatchData matchData = parserService.parse(tempFile);

      // Tworzenie nagłówka
      Header domainHeader = new Header(matchData.header().map_name(), matchData.header().server_name());

      // Aktualizacja encji
      Dem completedDem = dem.toBuilder()
              .header(domainHeader)
              .build();

      completedDem.getMetadata().markAsFinished(); // Ustaw status na DONE
      demRepository.save(completedDem);

      log.info("BACKGROUND: Demo processed and saved successfully.");

    } catch (Exception e) {
      log.error("BACKGROUND: Processing failed", e);
      dem.getMetadata().markAsFailed(); // Ustaw status na ERROR
      demRepository.save(dem);
    } finally {
      // Sprzątanie pliku tymczasowego z dysku serwera
      if (tempFile != null && tempFile.exists()) {
        boolean deleted = tempFile.delete();
        if (!deleted) {
          log.warn("Could not delete temporary file: {}", tempFile.getAbsolutePath());
        }
      }
    }
  }
}