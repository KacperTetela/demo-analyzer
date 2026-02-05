package demoanalyzer.com.dem.parser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import demoanalyzer.com.dem.parser.domain.model.CompleteMatchData;
import demoanalyzer.com.dem.parser.domain.model.header.Header;
import demoanalyzer.com.dem.parser.domain.model.raw.*;
import demoanalyzer.com.dem.parser.domain.model.stats.Adr;
import demoanalyzer.com.dem.parser.domain.model.stats.Kast;
import demoanalyzer.com.dem.parser.domain.model.stats.Rating;
import demoanalyzer.com.dem.parser.domain.service.ParserService;
import demoanalyzer.com.dem.parser.infrastructure.CsvDeserializer;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService {

  @Value("${parser.service.url}")
  private String parserServiceUrl;

  private final RestClient.Builder restClientBuilder;
  private final ObjectMapper objectMapper;

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
          }
        });

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
              return processZipStream(response.getBody());
            });
  }

  private CompleteMatchData processZipStream(InputStream zipStream) {
    // Inicjalizacja list
    Header header = null;
    List<Bomb> bombs = new ArrayList<>();
    List<Damages> damages = new ArrayList<>();
    List<Footsteps> footsteps = new ArrayList<>();
    List<GameInfo> gameInfos = new ArrayList<>();
    List<Grenades> grenades = new ArrayList<>();
    List<Infernos> infernos = new ArrayList<>();
    List<Kills> kills = new ArrayList<>();
    List<Rounds> rounds = new ArrayList<>();
    List<Shots> shots = new ArrayList<>();
    List<Smokes> smokes = new ArrayList<>();
    List<Ticks> ticks = new ArrayList<>();
    List<Adr> adrs = new ArrayList<>();
    List<Kast> kasts = new ArrayList<>();
    List<Rating> ratings = new ArrayList<>();

    try (ZipInputStream zis = new ZipInputStream(zipStream)) {
      ZipEntry entry;

      while ((entry = zis.getNextEntry()) != null) {
        String fileName = entry.getName();

        if ("header.json".equals(fileName)) {
          byte[] jsonBytes = zis.readAllBytes();
          header = objectMapper.readValue(jsonBytes, Header.class);

        } else if (fileName.endsWith(".csv")) {
          BufferedReader reader =
              new BufferedReader(new InputStreamReader(zis, StandardCharsets.UTF_8));

          switch (fileName) {
            case "bomb.csv" -> bombs = new CsvDeserializer<>(Bomb.class).deserialize(reader);
            case "damages.csv" ->
                damages = new CsvDeserializer<>(Damages.class).deserialize(reader);
            case "footsteps.csv" ->
                footsteps = new CsvDeserializer<>(Footsteps.class).deserialize(reader);
            case "game_info.csv", "gameInfos.csv" ->
                gameInfos = new CsvDeserializer<>(GameInfo.class).deserialize(reader);
            case "grenades.csv" ->
                grenades = new CsvDeserializer<>(Grenades.class).deserialize(reader);
            case "infernos.csv" ->
                infernos = new CsvDeserializer<>(Infernos.class).deserialize(reader);
            case "kills.csv" -> kills = new CsvDeserializer<>(Kills.class).deserialize(reader);
            case "rounds.csv" -> rounds = new CsvDeserializer<>(Rounds.class).deserialize(reader);
            case "shots.csv" -> shots = new CsvDeserializer<>(Shots.class).deserialize(reader);
            case "smokes.csv" -> smokes = new CsvDeserializer<>(Smokes.class).deserialize(reader);
            case "ticks.csv" -> ticks = new CsvDeserializer<>(Ticks.class).deserialize(reader);

            // Stats
            case "adr.csv" -> adrs = new CsvDeserializer<>(Adr.class).deserialize(reader);
            case "kast.csv" -> kasts = new CsvDeserializer<>(Kast.class).deserialize(reader);
            case "rating.csv" -> ratings = new CsvDeserializer<>(Rating.class).deserialize(reader);

            default -> log.warn("Skipping unknown CSV: {}", fileName);
          }
        }

        zis.closeEntry();
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to process ZIP stream", e);
    }

    if (header == null) {
      throw new IllegalStateException("Missing header.json in ZIP!");
    }

    return new CompleteMatchData(
        header, bombs, damages, footsteps, gameInfos, grenades, infernos, kills, rounds, shots,
        smokes, ticks, adrs, kasts, ratings);
  }
}
