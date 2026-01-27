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

  public static List<DemHandleResponse> from(List<Dem> dems) {
    if (dems == null) {
      return List.of();
    }

    return dems.stream()
        .map(
            dem ->
                new DemHandleResponse(
                    dem.getId(), dem.getMetadata().getCreatedAt(), dem.getMetadata().getStatus()))
        .toList();
  }
}
