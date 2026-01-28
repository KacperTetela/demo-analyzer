package demoanalyzer.com.dem.core.service;

import demoanalyzer.com.dem.core.domain.exception.DemNotFoundException;
import demoanalyzer.com.dem.core.domain.exception.UnauthorizedDemAccessException;
import demoanalyzer.com.dem.core.domain.model.Dem;
import demoanalyzer.com.dem.core.domain.model.metadata.Metadata;
import demoanalyzer.com.dem.core.domain.repository.DemRepository;
import demoanalyzer.com.dem.core.domain.service.DemService;
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
  private final DemProcessingService demProcessingService;

  @Override
  @Transactional
  public Dem handleDemFile(MultipartFile file, Long ownerId) {
    log.info("Processing file: {} for owner: {}", file.getOriginalFilename(), ownerId);

    // Repository provide ID of new dem
    Dem dem = Dem.builder().metadata(new Metadata(ownerId)).build();
    Dem savedDem = demRepository.save(dem);
    log.info(
        "Demo saved successfully. Owner ID from metadata: {}", savedDem.getMetadata().getOwnerId());

    demProcessingService.processDemo(savedDem.getId(), file);

    return savedDem;
  }

  @Override
  @Transactional(readOnly = true)
  public Dem getDem(Long demId, Long ownerId) {
    Dem dem = demRepository.findById(demId).orElseThrow(() -> new DemNotFoundException(demId));
    validateOwnership(dem, ownerId);
    return dem;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Dem> getUserDemos(Long ownerId) {
    return demRepository.findAll(ownerId);
  }

  private void validateOwnership(Dem dem, Long currentUserId) {
    Long demoOwnerId = dem.getMetadata().getOwnerId();

    if (!demoOwnerId.equals(currentUserId)) {
      throw new UnauthorizedDemAccessException();
    }
  }
}
