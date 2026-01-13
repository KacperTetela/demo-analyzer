package demoanalyzer.com.dem.api.dto;

import demoanalyzer.com.dem.domain.model.metadata.AnalysisStatus;
import demoanalyzer.com.dem.domain.model.metadata.Metadata;

import java.time.Instant;

public record DemStatusResponse(long demId, Instant createdAt, AnalysisStatus status) {
  public static DemStatusResponse from(Metadata metadata) {
    return new DemStatusResponse(
        metadata.getOwnerId(), metadata.getCreatedAt(), metadata.getStatus());
  }
}
