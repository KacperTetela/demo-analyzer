package demoanalyzer.com.dem.parser.domain.service;

import demoanalyzer.com.dem.parser.domain.model.CompleteMatchData;
import java.io.File;

public interface ParserService {
  CompleteMatchData parse(File file);
}
