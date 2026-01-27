package demoanalyzer.com.dem.api.dto.response;

import demoanalyzer.com.dem.domain.model.Dem;
import demoanalyzer.com.dem.domain.model.stats.StatsAdr;
import demoanalyzer.com.dem.domain.model.stats.StatsKast;
import demoanalyzer.com.dem.domain.model.stats.StatsRating;
import demoanalyzer.com.dem.domain.model.metadata.AnalysisStatus;
import demoanalyzer.com.dem.domain.model.team.TeamInfo;

import java.time.Instant;
import java.util.List;

public record DemDetailsResponse(
    long demId,
    Instant createdAt,
    Instant finishedAt,
    AnalysisStatus status,
    String mapName,
    String serverName,
    TeamInfo teamA,
    TeamInfo teamB,
    List<StatsAdr> statsAdr,
    List<StatsKast> statsKast,
    List<StatsRating> statsRating) {

  public static DemDetailsResponse from(Dem dem) {
    return new DemDetailsResponse(
        dem.getId(),
        dem.getMetadata().getCreatedAt(),
        dem.getMetadata().getFinishedAt(),
        dem.getMetadata().getStatus(),
        dem.getHeader().mapName(),
        dem.getHeader().serverName(),
        dem.getTeamA(),
        dem.getTeamB(),
        dem.getStatsAdr(),
        dem.getStatsKast(),
        dem.getStatsRating());
  }
}
