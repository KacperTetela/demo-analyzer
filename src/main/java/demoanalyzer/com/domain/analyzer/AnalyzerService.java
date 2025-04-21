package demoanalyzer.com.domain.analyzer;

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

/*    // for example
    EntryDTO entry1 = new EntryDTO(1, "Snax", true);
    EntryDTO entry2 = new EntryDTO(2, "Malbs", false);
    List<EntryDTO> entryDTOs = new ArrayList<>();
    entryDTOs.add(entry1);
    entryDTOs.add(entry2);
    return entryDTOs;*/
  }
}
