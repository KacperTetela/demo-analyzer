package demoanalyzer.com.dem.api.dto;

import demoanalyzer.com.dem.domain.model.header.Header;
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
    Header header,
    TeamInfo teamA,
    TeamInfo teamB,
    List<StatsAdr> statsAdr,
    List<StatsKast> statsKast,
    List<StatsRating> statsRating) {}
