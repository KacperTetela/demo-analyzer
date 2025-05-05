package demoanalyzer.com.infrastructure;

import demoanalyzer.com.domain.analyzer.AnalyzerService;
import demoanalyzer.com.domain.analyzer.GameDetailsDTO;
import demoanalyzer.com.domain.analyzer.clutch.ClutchDTO;
import demoanalyzer.com.domain.analyzer.entry.EntryDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {
  private final AnalyzerService analyzerService;

  public MainController(AnalyzerService analyzerService) {
    this.analyzerService = analyzerService;
  }

  @GetMapping("/info")
  public GameDetailsDTO getBasicReplayInfo() {
    return analyzerService.getBasicReplayInfo();
  }

  @GetMapping("/entry")
  public List<EntryDTO> getEntryInfo() {
    return analyzerService.getEntryInfo();
  }

  @GetMapping("/clutch")
  public List<ClutchDTO> getClutchInfo() {
    return analyzerService.getClutchInfo();
  }

  @GetMapping("/trade")
  public void getTradeInfo() {}
}
