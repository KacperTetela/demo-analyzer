package demoanalyzer.com.infrastructure;

import demoanalyzer.com.domain.analyzer.AnalyzerService;
import demoanalyzer.com.domain.analyzer.BasicDTO;
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
  public BasicDTO getBasicReplayInfo() {
    return analyzerService.getBasicReplayInfo();
  }

  @GetMapping("/entry")
  public List<EntryDTO> getEntryInfo() {
    return analyzerService.getEntryInfo();
  }
}
