package demoanalyzer.com.dem.core.api.dto.response;

import demoanalyzer.com.dem.core.domain.model.Dem;
import demoanalyzer.com.dem.core.domain.model.metadata.AnalysisStatus;
import demoanalyzer.com.dem.core.domain.model.team.TeamInfo;

import java.time.Instant;
import java.util.List;

public record DemHeaderResponse(
    long demId,
    Instant createdAt,
    AnalysisStatus status,
    String mapName,
    String serverName,
    TeamInfo teamA,
    TeamInfo teamB) {

  public static DemHeaderResponse from(Dem dem) {

    String mapName = dem.getHeader() != null ? dem.getHeader().mapName() : null;
    String serverName = dem.getHeader() != null ? dem.getHeader().serverName() : null;

    return new DemHeaderResponse(
        dem.getId(),
        dem.getMetadata().getCreatedAt(),
        dem.getMetadata().getStatus(),
        mapName,
        serverName,
        dem.getTeamA(),
        dem.getTeamB());
  }

  public static List<DemHeaderResponse> from(List<Dem> dems) {
    if (dems == null) {
      return List.of();
    }

    return dems.stream().map(DemHeaderResponse::from).toList();
  }
}
