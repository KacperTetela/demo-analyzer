package demoanalyzer.com.dem.core.service;

import demoanalyzer.com.dem.core.domain.model.Dem;
import demoanalyzer.com.dem.core.domain.model.header.Header;
import demoanalyzer.com.dem.core.domain.model.stats.StatsAdr;
import demoanalyzer.com.dem.core.domain.model.stats.StatsKast;
import demoanalyzer.com.dem.core.domain.model.stats.StatsRating;
import demoanalyzer.com.dem.core.domain.repository.DemRepository;
import demoanalyzer.com.dem.parser.domain.model.CompleteMatchData;
import demoanalyzer.com.dem.parser.domain.service.ParserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DemProcessingService {

  private final DemRepository demRepository;
  private final ParserService parserService;
  private final StatsMapper statsMapper;

  @Async // Ta metoda wykona się w osobnym wątku
  @Transactional
  public void processDemo(Long demId, File tempFile) {
    log.info("BACKGROUND: Starting processing for demo ID: {}", demId);

    // Znajdź encję, która została utworzona wcześniej
    Dem dem =
        demRepository
            .findById(demId)
            .orElseThrow(() -> new IllegalStateException("Dem not found: " + demId));

    try {
      CompleteMatchData matchData = parserService.parse(tempFile);

      // ... (header mapping)

      // 1. Mapowanie ADR
      List<StatsAdr> statsAdr = matchData.adrs().stream().map(statsMapper::mapToStatsAdr).toList();

      // 2. Mapowanie KAST
      List<StatsKast> statsKast =
          matchData.kasts().stream().map(statsMapper::mapToStatsKast).toList();

      // 3. Mapowanie Rating
      List<StatsRating> statsRating =
          matchData.ratings().stream().map(statsMapper::mapToStatsRating).toList();

      // Aktualizacja encji
      Dem completedDem =
          dem.toBuilder()
              .header(
                  new Header(
                      matchData.header().map_name(),
                      matchData.header().server_name())) // Przykład mapowania headera
              .statsAdr(statsAdr)
              .statsKast(statsKast)
              .statsRating(statsRating)
              .build();

      completedDem.getMetadata().markAsFinished();
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
