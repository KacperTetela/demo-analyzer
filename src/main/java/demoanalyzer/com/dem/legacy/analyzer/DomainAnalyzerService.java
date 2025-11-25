package demoanalyzer.com.dem.legacy.analyzer;

import demoanalyzer.com.dem.legacy.analyzer.clutch.ClutchAnalyzer;
import demoanalyzer.com.dem.legacy.analyzer.clutch.ClutchDTO;
import demoanalyzer.com.dem.legacy.analyzer.entry.EntryAnalyzer;
import demoanalyzer.com.dem.legacy.analyzer.entry.EntryDTO;
import demoanalyzer.com.dem.legacy.analyzer.sidewin.SideWinAnalyzer;
import demoanalyzer.com.dem.legacy.analyzer.sidewin.TeamSideWins;
import demoanalyzer.com.dem.legacy.analyzer.trade.TradeAnalyzer;
import demoanalyzer.com.dem.legacy.analyzer.trade.TradeDTO;
import demoanalyzer.com.dem.legacy.replay.conversion.gameplay.KillsEvent;
import demoanalyzer.com.dem.legacy.replay.conversion.gameplay.RoundsEvent;
import demoanalyzer.com.dem.legacy.replay.conversion.gameplay.TicksEvent;
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
    List<TicksEvent> ticksEvents =
        replayAdapter.getGameplayEvents(TicksEvent.class).stream()
            .sorted(Comparator.comparingLong(tickEvent -> tickEvent.tick()))
            .limit(10)
            .toList();
    Team teamA = extractTeam("t", ticksEvents, "a");
    Team teamB = extractTeam("ct", ticksEvents, "b");

    GameDetailsDTO gameDetailsDTO =
        new GameDetailsDTO(
            replayAdapter.getBasicReplayInfo().mapName(),
            replayAdapter.getBasicReplayInfo().serverName(),
            teamA,
            teamB);
    return gameDetailsDTO;
  }

  private Team extractTeam(String site, List<TicksEvent> ticksEvents, String teamName) {
    List<String> players =
        ticksEvents.stream()
            .filter(ticksEvent -> ticksEvent.side().equalsIgnoreCase(site))
            .map(TicksEvent::name)
            .toList();
    return new Team(site.toUpperCase(), players, teamName);
  }

  public List<EntryDTO> getEntryInfo() {
    List<KillsEvent> killsEvents = replayAdapter.getGameplayEvents(KillsEvent.class);
    List<RoundsEvent> roundsEvents = replayAdapter.getGameplayEvents(RoundsEvent.class);

    return entryAnalyzer.analyzeEntryFrags(killsEvents, roundsEvents);
  }

  public List<ClutchDTO> getClutchInfo() {
    List<KillsEvent> killsEvents = replayAdapter.getGameplayEvents(KillsEvent.class);
    List<RoundsEvent> roundsEvents = replayAdapter.getGameplayEvents(RoundsEvent.class);

    return clutchAnalyzer.analyzeClutch(killsEvents, roundsEvents,getBasicReplayInfo());
  }

  public List<TeamSideWins> getSideWinsInfo() {
    List<RoundsEvent> roundsEvents = replayAdapter.getGameplayEvents(RoundsEvent.class);
    return sideWinAnalyzer.analyzeTeamsSideWins(getBasicReplayInfo(), roundsEvents);
  }

  public List<TradeDTO> getTradeInfo() {
    List<KillsEvent> killsEvents = replayAdapter.getGameplayEvents(KillsEvent.class);
    return tradeAnalyzer.analyzeTrade(killsEvents);
  }
}
