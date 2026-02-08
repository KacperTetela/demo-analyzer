package demoanalyzer.com.dem.core.service;

import demoanalyzer.com.dem.analyzer.api.AnalyzerApi;
import demoanalyzer.com.dem.analyzer.api.dto.AnalysisResult;
import demoanalyzer.com.dem.core.domain.model.Dem;
import demoanalyzer.com.dem.core.domain.model.header.Header;
import demoanalyzer.com.dem.core.domain.model.stats.awpy.StatsAdr;
import demoanalyzer.com.dem.core.domain.model.stats.awpy.StatsKast;
import demoanalyzer.com.dem.core.domain.model.stats.awpy.StatsRating;
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
  private final AnalyzerApi analyzer;

  @Async
  @Transactional
  public void processDemo(Long demId, File tempFile) {
    log.info("BACKGROUND: Starting processing for demo ID: {}", demId);

    // 1. Pobieramy encję (stan początkowy, np. status PROCESSING)
    Dem dem =
        demRepository
            .findById(demId)
            .orElseThrow(() -> new IllegalStateException("Dem not found: " + demId));

    try {
      // 2. Parsowanie pliku .dem (Python/Parser Service)
      CompleteMatchData matchData = parserService.parse(tempFile);

      // 3. Analiza logiczna (Java Logic)
      // Tutaj dzieje się magia: entry, trades, clutches, scores
      AnalysisResult analysisResult = analyzer.analyze(matchData);

      // 4. Mapowanie prostych statystyk (ADR, KAST, Rating)
      List<StatsAdr> statsAdr = matchData.adrs().stream().map(statsMapper::mapToStatsAdr).toList();
      List<StatsKast> statsKast =
          matchData.kasts().stream().map(statsMapper::mapToStatsKast).toList();
      List<StatsRating> statsRating =
          matchData.ratings().stream().map(statsMapper::mapToStatsRating).toList();

      // 5. Budowanie kompletnego obiektu Dem (Aktualizacja encji)
      Dem completedDem =
          dem.toBuilder()
              // Podstawowe info z nagłówka
              .header(new Header(matchData.header().map_name(), matchData.header().server_name()))

              // Dane z AnalysisResult (Nasze nowe algorytmy)
              .teamA(analysisResult.teamA())
              .teamB(analysisResult.teamB())
              .entryFrags(analysisResult.entryFrags()) // <--- NOWE
              .clutches(analysisResult.clutches()) // <--- NOWE
              .tradeKills(analysisResult.trades()) // <--- NOWE
              .sideWins(analysisResult.sideWins()) // <--- NOWE

              // Statystyki szczegółowe
              .statsAdr(statsAdr)
              .statsKast(statsKast)
              .statsRating(statsRating)
              .build();

      // 6. Oznaczenie sukcesu i zapis w bazie
      completedDem.getMetadata().markAsFinished();
      demRepository.save(completedDem);

      log.info("BACKGROUND: Demo processed and saved successfully for ID: {}", demId);

    } catch (Exception e) {
      log.error("BACKGROUND: Processing failed for demo ID: " + demId, e);

      // Obsługa błędu - zmiana statusu na FAILED
      dem.getMetadata().markAsFailed();
      demRepository.save(dem);

    } finally {
      // Sprzątanie pliku
      if (tempFile != null && tempFile.exists()) {
        if (!tempFile.delete()) {
          log.warn("Could not delete temporary file: {}", tempFile.getAbsolutePath());
        }
      }
    }
  }
}
