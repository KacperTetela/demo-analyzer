package demoanalyzer.com.dem.legacy.infrastructure;

import demoanalyzer.com.dem.legacy.analyzer.DomainAnalyzerService;
import demoanalyzer.com.dem.legacy.analyzer.GameDetailsDTO;
import demoanalyzer.com.dem.legacy.analyzer.clutch.ClutchDTO;
import demoanalyzer.com.dem.legacy.analyzer.entry.EntryDTO;
import demoanalyzer.com.dem.legacy.analyzer.sidewin.TeamSideWins;
import demoanalyzer.com.dem.legacy.analyzer.trade.TradeDTO;
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
  public List<TradeDTO> getTradeInfo() {
    return domainAnalyzerService.getTradeInfo();
  }

  @GetMapping("/side-wins")
  public List<TeamSideWins> getSideWinsInfo() {
    return domainAnalyzerService.getSideWinsInfo();
  }
}
