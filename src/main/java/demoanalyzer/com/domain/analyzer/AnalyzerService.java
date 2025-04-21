package demoanalyzer.com.domain.analyzer;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyzerService {

  private ReplayAdapter replayAdapter;

  public AnalyzerService(ReplayAdapter replayAdapter) {
    this.replayAdapter = replayAdapter;
  }

  public BasicDTO getBasicReplayInfo() {
    return replayAdapter.getBasicReplayInfo();
  }

  public List<EntryDTO> getEntryInfo() {

    EntryDTO entry1 = new EntryDTO(1, "Snax", true);
    EntryDTO entry2 = new EntryDTO(2, "Malbs", false);
    List<EntryDTO> entryDTOs = new ArrayList<>();
    entryDTOs.add(entry1);
    entryDTOs.add(entry2);

    return entryDTOs;
  }
}
