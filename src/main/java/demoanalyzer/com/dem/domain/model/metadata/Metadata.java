package demoanalyzer.com.dem.domain.model.metadata;

import java.time.Instant;

public class Metadata {
  private final Long ownerId;
  private final Instant createdAt = Instant.now();
  private Instant finishedAt;
  private AnalysisStatus status = AnalysisStatus.PENDING;

  public Metadata(Long ownerId) {
    this.ownerId = ownerId;
  }

  public Long getOwnerId() {
    return ownerId;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getFinishedAt() {
    return finishedAt;
  }

  public AnalysisStatus getStatus() {
    return status;
  }

  public Metadata(Long ownerId, AnalysisStatus status, Instant finishedAt) {
    this.ownerId = ownerId;
    this.status = status;
    this.finishedAt = finishedAt;
  }

  public void setFinishedAt(Instant finishedAt) {
    this.finishedAt = finishedAt;
  }

  public void setStatus(AnalysisStatus status) {
    this.status = status;
  }
}
