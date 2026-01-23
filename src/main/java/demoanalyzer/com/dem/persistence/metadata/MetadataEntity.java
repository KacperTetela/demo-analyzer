package demoanalyzer.com.dem.persistence.metadata;

import demoanalyzer.com.dem.domain.model.metadata.AnalysisStatus;
import demoanalyzer.com.dem.domain.model.metadata.Metadata;
import demoanalyzer.com.user.auth.persistence.AuthUserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetadataEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = false)
  private AuthUserEntity owner;

  @NonNull private Instant createdAt;

  private Instant finishedAt;

  @Enumerated(EnumType.STRING)
  private AnalysisStatus status;

  public static MetadataEntity from(Metadata metadata) {
    return new MetadataEntity(
        metadata.getOwnerId(),
        metadata.getCreatedAt(),
        metadata.getFinishedAt(),
        metadata.getStatus());
  }

  public static Metadata from(MetadataEntity metadataEntity) {
    return new Metadata(
        metadataEntity.owner.getId(),
        metadataEntity.getCreatedAt(),
        metadataEntity.getFinishedAt(),
        metadataEntity.getStatus());
  }
}
