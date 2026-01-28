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

@Slf4j
@Service
@RequiredArgsConstructor
public class DemProcessingService {

  private final DemRepository demRepository;
  private final ParserService parserService;

  @Async
  @Transactional
  public void processDemo(Long demId, MultipartFile file) {
    log.info("Starting processing for demo ID: {}", demId);

    // find Dem entity based on demId
    Dem dem =
        demRepository
            .findById(demId)
            .orElseThrow(() -> new IllegalStateException("Dem not found: " + demId));

    try {

      // Parse the demo file
      CompleteMatchData matchData = parserService.parse(file);

      // Create Header from parsed data
      Header domainHeader =
          new Header(matchData.header().map_name(), matchData.header().server_name());

      // Update encji
      Dem completedDem = dem.toBuilder().header(domainHeader).build();

      completedDem.getMetadata().markAsFinished();
      demRepository.save(completedDem);

      log.info("Demo saved successfully.");

    } catch (Exception e) {
      log.error("Processing failed", e);
      dem.getMetadata().markAsFailed();
      demRepository.save(dem);
    }
  }
}
