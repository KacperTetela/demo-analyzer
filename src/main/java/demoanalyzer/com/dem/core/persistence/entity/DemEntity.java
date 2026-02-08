package demoanalyzer.com.dem.core.persistence.entity;

import demoanalyzer.com.dem.core.domain.model.stats.analyzer.Clutch;
import demoanalyzer.com.dem.core.domain.model.stats.analyzer.Entry;
import demoanalyzer.com.dem.core.domain.model.stats.analyzer.TeamSideWins;
import demoanalyzer.com.dem.core.domain.model.stats.analyzer.trade.Trade;
import demoanalyzer.com.dem.core.domain.model.stats.awpy.StatsAdr;
import demoanalyzer.com.dem.core.domain.model.stats.awpy.StatsKast;
import demoanalyzer.com.dem.core.domain.model.stats.awpy.StatsRating;
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

  @Embedded private MetadataEntity metadata;

  @Embedded private HeaderEntity header;

  @JdbcTypeCode(SqlTypes.JSON)
  private TeamInfo teamA;

  @JdbcTypeCode(SqlTypes.JSON)
  private TeamInfo teamB;

  // --- NOWE POLA ---

  @Builder.Default
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb") // Opcjonalnie: wymusza typ w Postgresie
  private List<Entry> entryFrags = new ArrayList<>();

  @Builder.Default
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb")
  private List<Clutch> clutches = new ArrayList<>();

  @Builder.Default
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb")
  private List<Trade> tradeKills = new ArrayList<>();

  @Builder.Default
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb")
  private List<TeamSideWins> sideWins = new ArrayList<>();

  // ----------------

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
