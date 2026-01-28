package demoanalyzer.com.dem.core.domain.model;

import demoanalyzer.com.dem.core.domain.exception.InvalidDemDomainException;
import demoanalyzer.com.dem.core.domain.model.header.Header;
import demoanalyzer.com.dem.core.domain.model.stats.StatsAdr;
import demoanalyzer.com.dem.core.domain.model.stats.StatsKast;
import demoanalyzer.com.dem.core.domain.model.stats.StatsRating;
import demoanalyzer.com.dem.core.domain.model.metadata.Metadata;
import demoanalyzer.com.dem.core.domain.model.team.TeamInfo;

import java.util.ArrayList;
import java.util.List;

public class Dem {

  private final Long id;
  private final Metadata metadata;
  private final Header header;
  private final TeamInfo teamA;
  private final TeamInfo teamB;
  private final List<StatsAdr> statsAdr;
  private final List<StatsKast> statsKast;
  private final List<StatsRating> statsRating;

  private Dem(Builder builder) {
    this.id = builder.id;
    this.metadata = builder.metadata;
    this.header = builder.header;
    this.teamA = builder.teamA;
    this.teamB = builder.teamB;
    // List protection: if builder is null, we create an empty list
    this.statsAdr = builder.statsAdr != null ? builder.statsAdr : new ArrayList<>();
    this.statsKast = builder.statsKast != null ? builder.statsKast : new ArrayList<>();
    this.statsRating = builder.statsRating != null ? builder.statsRating : new ArrayList<>();
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
    return new ArrayList<>(statsAdr);
  }

  public List<StatsKast> getStatsKast() {
    return new ArrayList<>(statsKast);
  }

  public List<StatsRating> getStatsRating() {
    return new ArrayList<>(statsRating);
  }

  public Builder toBuilder() {
    return new Builder()
        .id(this.id)
        .metadata(this.metadata)
        .header(this.header)
        .teamA(this.teamA)
        .teamB(this.teamB)
        .statsAdr(this.statsAdr)
        .statsKast(this.statsKast)
        .statsRating(this.statsRating);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Long id;
    private Metadata metadata;
    private Header header;
    private TeamInfo teamA;
    private TeamInfo teamB;
    // List protection: if builder is null, we create an empty list
    private List<StatsAdr> statsAdr = new ArrayList<>();
    private List<StatsKast> statsKast = new ArrayList<>();
    private List<StatsRating> statsRating = new ArrayList<>();

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder metadata(Metadata metadata) {
      this.metadata = metadata;
      return this;
    }

    public Builder header(Header header) {
      this.header = header;
      return this;
    }

    public Builder teamA(TeamInfo teamA) {
      this.teamA = teamA;
      return this;
    }

    public Builder teamB(TeamInfo teamB) {
      this.teamB = teamB;
      return this;
    }

    public Builder statsAdr(List<StatsAdr> statsAdr) {
      this.statsAdr = statsAdr;
      return this;
    }

    public Builder statsKast(List<StatsKast> statsKast) {
      this.statsKast = statsKast;
      return this;
    }

    public Builder statsRating(List<StatsRating> statsRating) {
      this.statsRating = statsRating;
      return this;
    }

    public Dem build() {
      if (this.metadata == null) {
        throw new InvalidDemDomainException("Dem cannot be created without Metadata.");
      }

      if (this.metadata.getOwnerId() == null) {
        throw new InvalidDemDomainException("Dem must belong to a valid Owner (ownerId is null).");
      }

      return new Dem(this);
    }
  }
}
