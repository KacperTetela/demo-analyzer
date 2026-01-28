package demoanalyzer.com.dem.core.api.dto.response;

import demoanalyzer.com.dem.core.domain.model.Dem;
import demoanalyzer.com.dem.core.domain.model.metadata.AnalysisStatus;

import java.time.Instant;

public record DemHandleResponse(long demId, Instant createdAt, AnalysisStatus status) {
  public static DemHandleResponse from(Dem dem) {
    return new DemHandleResponse(
        dem.getId(), dem.getMetadata().getCreatedAt(), dem.getMetadata().getStatus());
  }
}
