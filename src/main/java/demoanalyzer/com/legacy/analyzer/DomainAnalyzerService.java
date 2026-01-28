package demoanalyzer.com.legacy.analyzer;

import demoanalyzer.com.legacy.analyzer.clutch.ClutchAnalyzer;
import demoanalyzer.com.legacy.analyzer.clutch.ClutchDTO;
import demoanalyzer.com.legacy.analyzer.entry.EntryAnalyzer;
import demoanalyzer.com.legacy.analyzer.entry.EntryDTO;
import demoanalyzer.com.legacy.analyzer.sidewin.SideWinAnalyzer;
import demoanalyzer.com.legacy.analyzer.sidewin.TeamSideWins;
import demoanalyzer.com.legacy.analyzer.trade.TradeAnalyzer;
import demoanalyzer.com.legacy.analyzer.trade.TradeDTO;
import demoanalyzer.com.dem.parser.domain.model.raw.Kills;
import demoanalyzer.com.dem.parser.domain.model.raw.Rounds;
import demoanalyzer.com.dem.parser.domain.model.raw.Ticks;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class DomainAnalyzerService {

  private ReplayAdapter replayAdapter;
  private EntryAnalyzer entryAnalyzer;
  private ClutchAnalyzer clutchAnalyzer;
  private SideWinAnalyzer sideWinAnalyzer;
  private TradeAnalyzer tradeAnalyzer;

  public DomainAnalyzerService(ReplayAdapter replayAdapter) {
    this.replayAdapter = replayAdapter;
    this.entryAnalyzer = new EntryAnalyzer();
    this.clutchAnalyzer = new ClutchAnalyzer();
    this.sideWinAnalyzer = new SideWinAnalyzer();
    this.tradeAnalyzer = new TradeAnalyzer();
  }

  public GameDetailsDTO getBasicReplayInfo() {
    List<Ticks> ticks =
        replayAdapter.getGameplayEvents(Ticks.class).stream()
            .sorted(Comparator.comparingLong(tickEvent -> tickEvent.tick()))
            .limit(10)
            .toList();
    Team teamA = extractTeam("t", ticks, "a");
    Team teamB = extractTeam("ct", ticks, "b");

    GameDetailsDTO gameDetailsDTO =
        new GameDetailsDTO(
            replayAdapter.getBasicReplayInfo().mapName(),
            replayAdapter.getBasicReplayInfo().serverName(),
            teamA,
            teamB);
    return gameDetailsDTO;
  }

  private Team extractTeam(String site, List<Ticks> ticks, String teamName) {
    List<String> players =
        ticks.stream()
            .filter(ticks -> ticks.side().equalsIgnoreCase(site))
            .map(Ticks::name)
            .toList();
    return new Team(site.toUpperCase(), players, teamName);
  }

  public List<EntryDTO> getEntryInfo() {
    List<Kills> kills = replayAdapter.getGameplayEvents(Kills.class);
    List<Rounds> rounds = replayAdapter.getGameplayEvents(Rounds.class);

    return entryAnalyzer.analyzeEntryFrags(kills, rounds);
  }

  public List<ClutchDTO> getClutchInfo() {
    List<Kills> kills = replayAdapter.getGameplayEvents(Kills.class);
    List<Rounds> rounds = replayAdapter.getGameplayEvents(Rounds.class);

    return clutchAnalyzer.analyzeClutch(kills, rounds,getBasicReplayInfo());
  }

  public List<TeamSideWins> getSideWinsInfo() {
    List<Rounds> rounds = replayAdapter.getGameplayEvents(Rounds.class);
    return sideWinAnalyzer.analyzeTeamsSideWins(getBasicReplayInfo(), rounds);
  }

  public List<TradeDTO> getTradeInfo() {
    List<Kills> kills = replayAdapter.getGameplayEvents(Kills.class);
    return tradeAnalyzer.analyzeTrade(kills);
  }
}
