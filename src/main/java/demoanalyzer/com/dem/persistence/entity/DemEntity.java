package demoanalyzer.com.dem.persistence.entity;

import demoanalyzer.com.dem.domain.model.stats.StatsAdr;
import demoanalyzer.com.dem.domain.model.stats.StatsKast;
import demoanalyzer.com.dem.domain.model.stats.StatsRating;
import demoanalyzer.com.dem.domain.model.team.TeamInfo;
import demoanalyzer.com.dem.persistence.entity.header.HeaderEntity;
import demoanalyzer.com.dem.persistence.entity.metadata.MetadataEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dem")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded private MetadataEntity metadata;

  @Embedded private HeaderEntity header;

  @JdbcTypeCode(SqlTypes.JSON)
  private TeamInfo teamA;

  @JdbcTypeCode(SqlTypes.JSON)
  private TeamInfo teamB;

  @Builder.Default
  @JdbcTypeCode(SqlTypes.JSON)
  private List<StatsAdr> statsAdr = new ArrayList<>();

  @Builder.Default
  @JdbcTypeCode(SqlTypes.JSON)
  private List<StatsKast> statsKast = new ArrayList<>();

  @Builder.Default
  @JdbcTypeCode(SqlTypes.JSON)
  private List<StatsRating> statsRating = new ArrayList<>();
}
