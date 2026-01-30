package demoanalyzer.com.dem.analyzer.api.dto;

import demoanalyzer.com.dem.analyzer.internal.model.Clutch;
import demoanalyzer.com.dem.analyzer.internal.model.Entry;
import demoanalyzer.com.dem.analyzer.internal.model.trade.Trade;
import demoanalyzer.com.dem.core.domain.model.team.TeamInfo;

import java.util.List;

public record AnalysisResult(
    TeamInfo teamA,
    TeamInfo teamB,
    List<Entry> entryFrags,
    List<Clutch> clutches,
    List<Trade> trades) {}
