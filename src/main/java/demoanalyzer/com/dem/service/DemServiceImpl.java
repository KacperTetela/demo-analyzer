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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DemServiceImpl implements DemService {

  private final DemRepository demRepository;

  @Override
  @Transactional
  public Dem handleDemFile(MultipartFile file, Long ownerId) {
    log.info("Processing file: {} for owner: {}", file.getOriginalFilename(), ownerId);

    Dem dem = Dem.builder().metadata(new Metadata(ownerId)).build();

    // Repository provide ID of new dem
    Dem savedDem = demRepository.save(dem);

    log.info(
        "Demo saved successfully. Owner ID from metadata: {}", savedDem.getMetadata().getOwnerId());
    return savedDem;
  }

  @Override
  @Transactional(readOnly = true)
  public Dem getDemStatus(Long demId, Long ownerId) {
    Dem dem = demRepository.findById(demId).orElseThrow(() -> new DemNotFoundException(demId));
    validateOwnership(dem, ownerId);
    return dem;
  }

  @Override
  public List<Dem> getAllDemsStatuses(Long ownerId) {
    return List.of();
  }

  @Override
  @Transactional(readOnly = true)
  public Dem getDemDetails(Long demId, Long ownerId) {
    Dem dem = demRepository.findById(demId).orElseThrow(() -> new DemNotFoundException(demId));
    validateOwnership(dem, ownerId);
    return dem;
  }

  private void validateOwnership(Dem dem, Long currentUserId) {
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
