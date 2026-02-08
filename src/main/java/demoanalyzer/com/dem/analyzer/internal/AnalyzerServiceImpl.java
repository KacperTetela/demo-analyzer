package demoanalyzer.com.dem.analyzer.internal;

import demoanalyzer.com.dem.analyzer.api.AnalyzerApi;
import demoanalyzer.com.dem.analyzer.api.dto.AnalysisResult;
import demoanalyzer.com.dem.analyzer.api.model.Clutch;
import demoanalyzer.com.dem.analyzer.api.model.Entry;
import demoanalyzer.com.dem.analyzer.api.model.PlayerStats;
import demoanalyzer.com.dem.analyzer.api.model.TeamSideWins;
import demoanalyzer.com.dem.analyzer.api.model.trade.Trade;
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

  // Wstrzykujemy kalkulator statystyk graczy
  private final PlayerStatsCalculator playerStatsCalculator;

  @Override
  public AnalysisResult analyze(CompleteMatchData rawData) {
    // 1. Najpierw musimy ustalić kto jest kim i jaki jest wynik
    MatchTeams teams = teamSideCalculator.calculateTeams(rawData.kills(), rawData.rounds());

    // 2. Analiza Entry Fragów
    List<Entry> entryFrags = entryAnalyzer.analyze(rawData.kills(), rawData.rounds());

    // 3. Analiza Trade Killi
    List<Trade> tradeKills = tradeAnalyzer.analyze(rawData.kills());

    // 4. Analiza Clutchy
    List<Clutch> clutches = clutchAnalyzer.analyze(rawData.kills(), rawData.rounds(), teams);

    // 5. Analiza stron (CT/T wins)
    List<TeamSideWins> sideWins = sideWinCalculator.analyze(teams, rawData.rounds());

    // 6. NOWOŚĆ: Obliczanie zagregowanych statystyk graczy
    // Przekazujemy surowe dane ORAZ wyniki wcześniejszych analiz (entry, clutch, trade)
    List<PlayerStats> playerStats =
        playerStatsCalculator.calculate(rawData, entryFrags, clutches, tradeKills);

    // 7. Mapowanie i zwracanie wyniku
    return new AnalysisResult(
        teamMapper.mapToTeamInfo(teams.teamA()),
        teamMapper.mapToTeamInfo(teams.teamB()),
        sideWins,
        playerStats // Przekazujemy obliczone statystyki graczy
        );
  }
}
