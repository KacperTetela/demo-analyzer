package demoanalyzer.com.dem.parser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import demoanalyzer.com.dem.parser.domain.model.CompleteMatchData;
import demoanalyzer.com.dem.parser.domain.service.ParserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService {
  @Value("${parser.service.url}") // np. https://develop.api.parser.kacpertetela.ddns.net
  private String parserServiceUrl;

  private final RestClient.Builder restClientBuilder;

  @Override
  public CompleteMatchData parse(MultipartFile file) {
    log.info("Sending demo to parser microservice: {}", parserServiceUrl);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add(
        "file",
        new InputStreamResource(file) {
          @Override
          public String getFilename() {
            return "demo.dem";
          }

          @Override
          public long contentLength() {
            return -1;
          } // Ważne dla streamowania
        });

    // 1. Strzał do API
    return restClientBuilder
        .build()
        .post()
        .uri(parserServiceUrl + "/demo")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(body)
        .exchange(
            (request, response) -> {
              if (response.getStatusCode().isError()) {
                throw new RuntimeException("Parser failed: " + response.getStatusCode());
              }
              // 2. Przetwarzanie ZIP-a w locie
              return processZipStream(response.getBody());
            });
  }

  private CompleteMatchData processZipStream(InputStream zipStream) {
    return null;
  }
}
