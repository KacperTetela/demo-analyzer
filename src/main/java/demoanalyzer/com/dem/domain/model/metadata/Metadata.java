package demoanalyzer.com.dem.domain.model.metadata;

import java.time.Instant;

public class Metadata {
  private final Long ownerId;
  private final Instant createdAt;
  private Instant finishedAt;
  private AnalysisStatus status;

  // init
  public Metadata(Long ownerId) {
    this.ownerId = ownerId;
    this.createdAt = Instant.now();
    this.status = AnalysisStatus.PENDING;
  }

  public Metadata(Long ownerId, Instant createdAt, Instant finishedAt, AnalysisStatus status) {
    this.ownerId = ownerId;
    this.createdAt = createdAt;
    this.finishedAt = finishedAt;
    this.status = status;
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

  public void markAsFinished() {
    this.status = AnalysisStatus.COMPLETED;
    this.finishedAt = Instant.now();
  }

  public void markAsFailed() {
    this.status = AnalysisStatus.FAILED;
    this.finishedAt = Instant.now();
  }
}
