package demoanalyzer.com.infrastructure;

import demoanalyzer.com.domain.analyzer.AnalyzerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final AnalyzerService analyzerService;

    public MainController(AnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    @GetMapping
    public String getMapName() {
        return analyzerService.getMapName();
    }

}
