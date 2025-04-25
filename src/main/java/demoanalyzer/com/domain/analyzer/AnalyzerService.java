package demoanalyzer.com.domain.analyzer;

import demoanalyzer.com.domain.analyzer.clutch.ClutchDTO;
import demoanalyzer.com.domain.analyzer.entry.EntryAnalyzer;
import demoanalyzer.com.domain.analyzer.entry.EntryDTO;
import demoanalyzer.com.domain.replay.conversion.gameplay.KillsEvent;
import demoanalyzer.com.domain.replay.conversion.gameplay.RoundsEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyzerService {

  private ReplayAdapter replayAdapter;
  private final EntryAnalyzer entryAnalyzer;

  public AnalyzerService(ReplayAdapter replayAdapter) {
    this.replayAdapter = replayAdapter;
    this.entryAnalyzer = new EntryAnalyzer();
  }

  public BasicDTO getBasicReplayInfo() {
    return replayAdapter.getBasicReplayInfo();
  }

  public List<EntryDTO> getEntryInfo() {
    List<KillsEvent> killsEvents = replayAdapter.getGameplayEvents(KillsEvent.class);
    List<RoundsEvent> roundsEvents = replayAdapter.getGameplayEvents(RoundsEvent.class);

    return entryAnalyzer.analyzeEntryFrags(killsEvents, roundsEvents);
  }

  public List<ClutchDTO> getClutchInfo() {
    return null;
  }

  public void getInformationAboutSquads() {}
}
