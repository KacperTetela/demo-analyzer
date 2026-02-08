package demoanalyzer.com.dem.analyzer.api.dto;

import demoanalyzer.com.dem.analyzer.api.model.PlayerStats;
import demoanalyzer.com.dem.analyzer.api.model.TeamSideWins;
import demoanalyzer.com.dem.core.domain.model.team.TeamInfo;

import java.util.List;

public record AnalysisResult(
        TeamInfo teamA,
        TeamInfo teamB,
        List<TeamSideWins> sideWins,
        List<PlayerStats> playerStats
) {}