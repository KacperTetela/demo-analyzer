package demoanalyzer.com.dem.core.service;

import demoanalyzer.com.dem.analyzer.api.AnalyzerApi;
import demoanalyzer.com.dem.analyzer.api.dto.AnalysisResult;
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
  private final AnalyzerApi analyzer;

  @Async
  @Transactional
  public void processDemo(Long demId, File tempFile) {
    log.info("Starting processing for demo ID: {}", demId);
    Dem dem = demRepository.findById(demId)
            .orElseThrow(() -> new IllegalStateException("Dem not found: " + demId));

    try {
      // 1. Parsowanie
      CompleteMatchData matchData = parserService.parse(tempFile);

      // 2. Analiza (zwraca AnalysisResult z listą PlayerStats)
      AnalysisResult result = analyzer.analyze(matchData);

      // 3. Mapowanie surowych statystyk (opcjonalne, jeśli trzymasz je w bazie)
      List<StatsAdr> statsAdr = matchData.adrs().stream().map(statsMapper::mapToStatsAdr).toList();
      List<StatsKast> statsKast = matchData.kasts().stream().map(statsMapper::mapToStatsKast).toList();
      List<StatsRating> statsRating = matchData.ratings().stream().map(statsMapper::mapToStatsRating).toList();

      // 4. Aktualizacja Domeny
      Dem completedDem = dem.toBuilder()
              .header(new Header(matchData.header().map_name(), matchData.header().server_name()))
              .teamA(result.teamA())
              .teamB(result.teamB())
              // Wpisujemy wyniki analizy:
              .playerStats(result.playerStats())
              .sideWins(result.sideWins())
              // Wpisujemy surowe dane:
              .statsAdr(statsAdr)
              .statsKast(statsKast)
              .statsRating(statsRating)
              .build();

      // 5. Zapis
      completedDem.getMetadata().markAsFinished();
      demRepository.save(completedDem);
      log.info("Demo processed successfully.");

    } catch (Exception e) {
      log.error("Processing failed", e);
      dem.getMetadata().markAsFailed();
      demRepository.save(dem);
    } finally {
      if (tempFile != null && tempFile.exists()) tempFile.delete();
    }
  }
}