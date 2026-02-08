package demoanalyzer.com.dem.core.persistence.entity;

import demoanalyzer.com.dem.analyzer.api.model.PlayerStats;
import demoanalyzer.com.dem.analyzer.api.model.TeamSideWins;
import demoanalyzer.com.dem.core.domain.model.team.TeamInfo;
import demoanalyzer.com.dem.core.persistence.entity.header.HeaderEntity;
import demoanalyzer.com.dem.core.persistence.entity.metadata.MetadataEntity;
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

  @Embedded
  private MetadataEntity metadata;

  @Embedded
  private HeaderEntity header;

  @JdbcTypeCode(SqlTypes.JSON)
  private TeamInfo teamA;

  @JdbcTypeCode(SqlTypes.JSON)
  private TeamInfo teamB;

  @Builder.Default
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb")
  private List<PlayerStats> playerStats = new ArrayList<>();

  @Builder.Default
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb")
  private List<TeamSideWins> sideWins = new ArrayList<>();
}
