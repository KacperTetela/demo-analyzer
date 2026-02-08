package demoanalyzer.com.dem.analyzer.internal;

import demoanalyzer.com.dem.analyzer.api.AnalyzerApi;
import demoanalyzer.com.dem.analyzer.api.dto.AnalysisResult;
import demoanalyzer.com.dem.core.domain.model.stats.analyzer.Clutch;
import demoanalyzer.com.dem.core.domain.model.stats.analyzer.Entry;
import demoanalyzer.com.dem.core.domain.model.stats.analyzer.TeamSideWins;
import demoanalyzer.com.dem.core.domain.model.stats.analyzer.trade.Trade;
import demoanalyzer.com.dem.analyzer.internal.logic.*;
import demoanalyzer.com.dem.analyzer.internal.model.MatchTeams;
import demoanalyzer.com.dem.parser.domain.model.CompleteMatchData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyzerServiceImpl implements AnalyzerApi {

  private final TeamSideCalculator teamSideCalculator;
  private final EntryAnalyzer entryAnalyzer;
  private final TradeAnalyzer tradeAnalyzer;
  private final ClutchAnalyzer clutchAnalyzer;
  private final SideWinCalculator sideWinCalculator;
  private final TeamMapper teamMapper;

  @Override
  public AnalysisResult analyze(CompleteMatchData rawData) {
    // 1. Najpierw musimy ustalić kto jest kim i jaki jest wynik (TeamSideCalculator)
    // To jest KROK KRYTYCZNY - musi być pierwszy.
    MatchTeams teams = teamSideCalculator.calculateTeams(rawData.kills(), rawData.rounds());

    // 2. Analiza Entry Fragów (Niezależna od Teams, zależy tylko od Kills i Rounds)
    List<Entry> entryFrags = entryAnalyzer.analyze(rawData.kills(), rawData.rounds());

    // 3. Analiza Trade Killi (Niezależna, zależy tylko od Kills)
    List<Trade> tradeKills = tradeAnalyzer.analyze(rawData.kills());

    // 4. Analiza Clutchy (Zależy od Kills, Rounds ORAZ Teams - bo musimy wiedzieć kto jest w jakim
    // teamie)
    List<Clutch> clutches = clutchAnalyzer.analyze(rawData.kills(), rawData.rounds(), teams);

    // 5. Analiza stron (CT/T wins) (Zależy od Teams i Rounds)
    List<TeamSideWins> sideWins = sideWinCalculator.analyze(teams, rawData.rounds());

    // 6. Mapowanie i zwracanie wyniku
    // Używamy mappera, aby zamienić wewnętrzny obiekt Team (z logiką) na proste DTO TeamInfo
    return new AnalysisResult(
        teamMapper.mapToTeamInfo(teams.teamA()),
        teamMapper.mapToTeamInfo(teams.teamB()),
        entryFrags,
        clutches,
        tradeKills,
        sideWins);
  }
}
