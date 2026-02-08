package demoanalyzer.com.dem.analyzer.api.dto;

import demoanalyzer.com.dem.core.domain.model.stats.analyzer.Clutch;
import demoanalyzer.com.dem.core.domain.model.stats.analyzer.Entry;
import demoanalyzer.com.dem.core.domain.model.stats.analyzer.TeamSideWins;
import demoanalyzer.com.dem.core.domain.model.stats.analyzer.trade.Trade;
import demoanalyzer.com.dem.core.domain.model.team.TeamInfo;

import java.util.List;

public record AnalysisResult(
        TeamInfo teamA,
        TeamInfo teamB,
        List<Entry> entryFrags,
        List<Clutch> clutches,
        List<Trade> trades,
        List<TeamSideWins> sideWins
) {}