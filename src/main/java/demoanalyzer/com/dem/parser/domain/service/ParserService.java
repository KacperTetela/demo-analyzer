package demoanalyzer.com.dem.parser.domain.service;

import demoanalyzer.com.dem.parser.domain.model.CompleteMatchData;
import org.springframework.web.multipart.MultipartFile;

public interface ParserService {
  CompleteMatchData parse(MultipartFile file);
}
