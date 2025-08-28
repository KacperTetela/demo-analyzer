package demoanalyzer.com.infrastructure;

import demoanalyzer.com.domain.analyzer.DomainAnalyzerService;
import demoanalyzer.com.domain.replay.parserhandler.ParserHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class DemoController {

  private static final String DEMO_DIR = "src/main/resources/dem/";
  private final MainController mainController;

  private final DomainAnalyzerService domainAnalyzerService;

  public DemoController(MainController mainController, DomainAnalyzerService domainAnalyzerService) {
    this.mainController = mainController;
    this.domainAnalyzerService = domainAnalyzerService;
  }

  @PostMapping("/upload-demo")
  public String uploadAndAnalyzeDemo(@RequestParam("file") MultipartFile file) {
    try {
      // Clear demos folder
      cleanDemoDirectory();

      // Save received file
      String fileName = file.getOriginalFilename();
      Path filePath = Paths.get(DEMO_DIR + fileName);
      Files.write(filePath, file.getBytes());

      // Analyze
      return analyzeDemo(fileName);
    } catch (IOException e) {
      return "File processing error: " + e.getMessage();
    }
  }

  private void cleanDemoDirectory() throws IOException {
    File directory = new File(DEMO_DIR);
    if (directory.exists() && directory.isDirectory()) {
      for (File file : directory.listFiles()) {
        if (!file.isDirectory()) {
          file.delete();
        }
      }
    } else {
      Files.createDirectories(Paths.get(DEMO_DIR));
    }
  }

  private String analyzeDemo(String filePath) {
    ParserHandler parserHandler = new ParserHandler();
    parserHandler.parse(filePath);
    return mainController.getBasicReplayInfo().mapName();
  }
}
