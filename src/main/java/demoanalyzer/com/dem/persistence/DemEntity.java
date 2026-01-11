package demoanalyzer.com.dem.persistence;

import demoanalyzer.com.dem.domain.model.status.DemAnalysisStatus;
import demoanalyzer.com.dem.domain.model.header.Header;
import demoanalyzer.com.dem.domain.model.stats.StatsAdr;
import demoanalyzer.com.dem.domain.model.stats.StatsKast;
import demoanalyzer.com.dem.domain.model.stats.StatsRating;
import demoanalyzer.com.dem.domain.model.team.TeamInfo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "dem")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DemEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long ownerId;

  private Instant createdAt;

  @Enumerated(EnumType.STRING)
  private DemAnalysisStatus status;

  private Instant finishedAt;

  @JdbcTypeCode(SqlTypes.JSON)
  private Header header;

  @JdbcTypeCode(SqlTypes.JSON)
  private TeamInfo teamA;

  @JdbcTypeCode(SqlTypes.JSON)
  private TeamInfo teamB;

  @JdbcTypeCode(SqlTypes.JSON)
  private List<StatsAdr> statsAdr;

  @JdbcTypeCode(SqlTypes.JSON)
  private List<StatsKast> statsKast;

  @JdbcTypeCode(SqlTypes.JSON)
  private List<StatsRating> statsRating;
}
