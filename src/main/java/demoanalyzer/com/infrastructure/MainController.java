package demoanalyzer.com.infrastructure;

import demoanalyzer.com.domain.analyzer.DomainAnalyzerService;
import demoanalyzer.com.domain.analyzer.GameDetailsDTO;
import demoanalyzer.com.domain.analyzer.clutch.ClutchDTO;
import demoanalyzer.com.domain.analyzer.entry.EntryDTO;
import demoanalyzer.com.domain.analyzer.sidewin.TeamSideWinsDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {
  private final DomainAnalyzerService domainAnalyzerService;

  public MainController(DomainAnalyzerService domainAnalyzerService) {
    this.domainAnalyzerService = domainAnalyzerService;
  }

  @GetMapping("/info")
  public GameDetailsDTO getBasicReplayInfo() {
    return domainAnalyzerService.getBasicReplayInfo();
  }

  @GetMapping("/entry")
  public List<EntryDTO> getEntryInfo() {
    return domainAnalyzerService.getEntryInfo();
  }

  @GetMapping("/clutch")
  public List<ClutchDTO> getClutchInfo() {
    return domainAnalyzerService.getClutchInfo();
  }

  @GetMapping("/trade")
  public String getTradeInfo() {
    return "";
  }

  @GetMapping("/side-wins")
  public List<TeamSideWinsDTO> getAllTeamsSideWins() {
    return domainAnalyzerService.getAllTeamsSideWins();
  }
}
