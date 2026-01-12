package demoanalyzer.com.dem.domain.model;

import demoanalyzer.com.dem.domain.model.header.Header;
import demoanalyzer.com.dem.domain.model.stats.StatsAdr;
import demoanalyzer.com.dem.domain.model.stats.StatsKast;
import demoanalyzer.com.dem.domain.model.stats.StatsRating;
import demoanalyzer.com.dem.domain.model.metadata.Metadata;
import demoanalyzer.com.dem.domain.model.team.TeamInfo;

import java.util.List;

public class Dem {
  private Long id;
  private Metadata metadata;
  private Header header;
  private TeamInfo teamA;
  private TeamInfo teamB;
  private List<StatsAdr> statsAdr;
  private List<StatsKast> statsKast;
  private List<StatsRating> statsRating;

  // init Dem
  public Dem(Long ownerId) {
    this.metadata = new Metadata(ownerId);
  }
}
