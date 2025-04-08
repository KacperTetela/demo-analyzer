package demoanalyzer.com.domain.analyzer;

import demoanalyzer.com.domain.replay.ReplayService;
import org.springframework.stereotype.Service;

@Service
public class AnalyzerService {
    private final ReplayService replayService;

    public AnalyzerService(ReplayService replayService) {
        this.replayService = replayService;
    }

    public String getMapName() {
        return replayService.getMapName();
    }

}
