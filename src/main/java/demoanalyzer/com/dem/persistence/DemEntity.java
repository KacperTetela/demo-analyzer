package demoanalyzer.com.dem.persistence;

import demoanalyzer.com.dem.domain.model.Header;
import demoanalyzer.com.dem.domain.model.stats.StatsAdr;
import demoanalyzer.com.dem.domain.model.stats.StatsKast;
import demoanalyzer.com.dem.domain.model.stats.StatsRating;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "dem")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DemEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Instant createdAt;
  private Long ownerId;
  private Header header;
  private List<StatsAdr> statsAdrAll;
  private List<StatsAdr> statsAdrT;
  private List<StatsAdr> statsAdrCT;
  private List<StatsKast> statsKastAll;
  private List<StatsKast> statsKastT;
  private List<StatsKast> statsKastCT;
  private List<StatsRating> statsRatingAll;
  private List<StatsRating> statsRatingT;
  private List<StatsRating> statsRatingCT;
}
