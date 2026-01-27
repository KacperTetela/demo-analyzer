package demoanalyzer.com.dem.api.dto.response;

import demoanalyzer.com.dem.domain.model.Dem;
import demoanalyzer.com.dem.domain.model.metadata.AnalysisStatus;

import java.time.Instant;
import java.util.List;

public record DemHandleResponse(long demId, Instant createdAt, AnalysisStatus status) {
  public static DemHandleResponse from(Dem dem) {
    return new DemHandleResponse(
        dem.getId(), dem.getMetadata().getCreatedAt(), dem.getMetadata().getStatus());
  }
}
