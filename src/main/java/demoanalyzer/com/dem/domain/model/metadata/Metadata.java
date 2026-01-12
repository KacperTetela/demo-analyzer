package demoanalyzer.com.dem.domain.model.metadata;

import java.time.Instant;

public class Metadata {
  private final Long ownerId;
  private final Instant createdAt = Instant.now();
  private Instant finishedAt;
  private AnalysisStatus status;

  public Metadata(Long ownerId) {
    this.ownerId = ownerId;
  }
}
