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
    this.id = null;

    this.metadata = new Metadata(ownerId);
    this.header = new Header("", "");
  }

  public Long getId() {
    return id;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public Header getHeader() {
    return header;
  }

  public TeamInfo getTeamA() {
    return teamA;
  }

  public TeamInfo getTeamB() {
    return teamB;
  }

  public List<StatsAdr> getStatsAdr() {
    return statsAdr;
  }

  public List<StatsKast> getStatsKast() {
    return statsKast;
  }

  public List<StatsRating> getStatsRating() {
    return statsRating;
  }

  public void setHeader(Header header) {
    this.header = header;
  }

  public void setTeamA(TeamInfo teamA) {
    this.teamA = teamA;
  }

  public void setTeamB(TeamInfo teamB) {
    this.teamB = teamB;
  }

  public void setStatsAdr(List<StatsAdr> statsAdr) {
    this.statsAdr = statsAdr;
  }

  public void setStatsKast(List<StatsKast> statsKast) {
    this.statsKast = statsKast;
  }

  public void setStatsRating(List<StatsRating> statsRating) {
    this.statsRating = statsRating;
  }
}
