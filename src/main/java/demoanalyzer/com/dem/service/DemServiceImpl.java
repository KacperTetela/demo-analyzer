package demoanalyzer.com.dem.service;

import demoanalyzer.com.dem.domain.exception.DemNotFoundException;
import demoanalyzer.com.dem.domain.exception.UnauthorizedDemAccessException;
import demoanalyzer.com.dem.domain.model.Dem;
import demoanalyzer.com.dem.domain.model.header.Header;
import demoanalyzer.com.dem.domain.model.metadata.Metadata;
import demoanalyzer.com.dem.domain.repository.DemRepository;
import demoanalyzer.com.dem.domain.service.DemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class DemServiceImpl implements DemService {

  private final DemRepository demRepository;

  @Override
  @Transactional
  public Metadata handleDemFile(MultipartFile file, Long ownerId) {
    log.info("Processing file: {} for owner: {}", file.getOriginalFilename(), ownerId);

    // 1. TWORZENIE AGREGATU
    // Wykorzystujemy Twój konstruktor: new Dem(ownerId).
    // On automatycznie tworzy w środku obiekt Metadata z przypisanym ownerId.
    Dem dem = new Dem(ownerId);

    // 2. PARSOWANIE I UZUPEŁNIANIE DANYCH
    // Wywołujemy logikę parsowania, która zwróci nam dane (np. Header)
    Header parsedHeader = parseHeaderFromFile(file);

    // Nadpisujemy pusty Header (stworzony w konstruktorze) tym prawdziwym
    dem.setHeader(parsedHeader);

    // 3. ZAPIS
    // Repozytorium nadaje ID encji (jeśli jest skonfigurowane z JPA)
    Dem savedDem = demRepository.save(dem);

    log.info(
        "Demo saved successfully. Owner ID from metadata: {}", savedDem.getMetadata().getOwnerId());

    return savedDem.getMetadata();
  }

  @Override
  @Transactional(readOnly = true)
  public Metadata getDemStatus(Long demId, Long ownerId) {
    Dem dem = demRepository.findById(demId).orElseThrow(() -> new DemNotFoundException(demId));
    validateOwnership(dem, ownerId);
    return dem.getMetadata();
  }

  @Override
  @Transactional(readOnly = true)
  public Dem getDemDetails(Long demId, Long ownerId) {
    Dem dem = demRepository.findById(demId).orElseThrow(() -> new DemNotFoundException(demId));
    validateOwnership(dem, ownerId);
    return dem;
  }

  // --- Metody pomocnicze ---

  private void validateOwnership(Dem dem, Long currentUserId) {
    // Pobieramy ID właściciela z wewnątrz obiektu Dem -> Metadata
    Long demoOwnerId = dem.getMetadata().getOwnerId();

    if (!demoOwnerId.equals(currentUserId)) {
      throw new UnauthorizedDemAccessException();
    }
  }

  // Symulacja parsowania nagłówka
  private Header parseHeaderFromFile(MultipartFile file) {
    // Tu będzie logika czytania pliku .dem
    return new Header("de_inferno", "Faceit Level 10 Match");
  }
}
