package demoanalyzer.com.domain.analyzer;

import org.springframework.stereotype.Service;

@Service
public class AnalyzerService {

  private ReplayAdapter replayAdapter;

    public AnalyzerService(ReplayAdapter replayAdapter) {  //late binding
        this.replayAdapter = replayAdapter;
    }

    public BasicDTO getBasicReplayInfo() {
        return replayAdapter.getBasicReplayInfo();
    }

}
