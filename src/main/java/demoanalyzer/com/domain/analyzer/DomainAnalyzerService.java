package demoanalyzer.com.domain.analyzer;

import demoanalyzer.com.domain.analyzer.clutch.ClutchAnalyzer;
import demoanalyzer.com.domain.analyzer.clutch.ClutchDTO;
import demoanalyzer.com.domain.analyzer.entry.EntryAnalyzer;
import demoanalyzer.com.domain.analyzer.entry.EntryDTO;
import demoanalyzer.com.domain.analyzer.sidewin.SideWinAnalyzer;
import demoanalyzer.com.domain.analyzer.sidewin.TeamSideWinsDTO;
import demoanalyzer.com.domain.analyzer.trade.TradeAnalyzer;
import demoanalyzer.com.domain.analyzer.trade.TradeDTO;
import demoanalyzer.com.domain.replay.conversion.gameplay.KillsEvent;
import demoanalyzer.com.domain.replay.conversion.gameplay.RoundsEvent;
import demoanalyzer.com.domain.replay.conversion.gameplay.TicksEvent;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class DomainAnalyzerService {

  private ReplayAdapter replayAdapter;
  private final EntryAnalyzer entryAnalyzer;
  private final ClutchAnalyzer clutchAnalyzer;
  private final SideWinAnalyzer sideWinAnalyzer;
  private final TradeAnalyzer tradeAnalyzer;

  public DomainAnalyzerService(ReplayAdapter replayAdapter) {
    this.replayAdapter = replayAdapter;
    this.entryAnalyzer = new EntryAnalyzer();
    this.clutchAnalyzer = new ClutchAnalyzer(getBasicReplayInfo());
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

    return clutchAnalyzer.analyzeClutch(killsEvents, roundsEvents);
  }

  public List<TeamSideWinsDTO> getSideWinsInfo() {
    List<RoundsEvent> roundsEvents = replayAdapter.getGameplayEvents(RoundsEvent.class);
    return sideWinAnalyzer.analyzeTeamsSideWins(getBasicReplayInfo(), roundsEvents);
  }

  public List<TradeDTO> getTradeInfo() {
    List<KillsEvent> killsEvents = replayAdapter.getGameplayEvents(KillsEvent.class);
    return tradeAnalyzer.analyzeTrade(killsEvents);
  }
}
