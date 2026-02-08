package demoanalyzer.com.dem.core.api.dto.response;

import demoanalyzer.com.dem.analyzer.api.model.PlayerStats;
import demoanalyzer.com.dem.analyzer.api.model.TeamSideWins;
import demoanalyzer.com.dem.core.domain.model.Dem;
import demoanalyzer.com.dem.core.domain.model.stats.StatsAdr;
import demoanalyzer.com.dem.core.domain.model.stats.StatsKast;
import demoanalyzer.com.dem.core.domain.model.stats.StatsRating;
import demoanalyzer.com.dem.core.domain.model.metadata.AnalysisStatus;
import demoanalyzer.com.dem.core.domain.model.team.TeamInfo;

import java.time.Instant;
import java.util.List;

public record DemDetailsResponse(
    long demId,
    Instant createdAt,
    AnalysisStatus status,
    String mapName,
    String serverName,

    // Zespoły i wynik
    TeamInfo teamA,
    TeamInfo teamB,

    // Wynik szczegółowy (Side Wins)
    List<TeamSideWins> sideWins,

    // Tabela wyników (Scoreboard) - to frontend wyświetla w pętli
    List<PlayerStats> playerStats) {

  public static DemDetailsResponse from(Dem dem) {
    return new DemDetailsResponse(
        dem.getId(),
        dem.getMetadata().getCreatedAt(),
        dem.getMetadata().getStatus(),
        dem.getHeader().mapName(),
        dem.getHeader().serverName(),
        dem.getTeamA(),
        dem.getTeamB(),
        dem.getSideWins(),
        dem.getPlayerStats() // <-- Czysta lista prosto z domeny
        );
  }
}
