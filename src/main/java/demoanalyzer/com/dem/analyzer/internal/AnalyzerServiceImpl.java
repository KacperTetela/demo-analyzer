package demoanalyzer.com.dem.analyzer.internal;

import demoanalyzer.com.dem.analyzer.api.AnalyzerApi;
import demoanalyzer.com.dem.analyzer.api.dto.AnalysisResult;
import demoanalyzer.com.dem.analyzer.internal.logic.*;
import demoanalyzer.com.dem.analyzer.internal.model.MatchTeams;
import demoanalyzer.com.dem.parser.domain.model.CompleteMatchData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyzerServiceImpl implements AnalyzerApi {

  private final TeamSideCalculator teamSideCalculator;
  private final EntryAnalyzer entryAnalyzer;
  private final TradeAnalyzer tradeAnalyzer;
  private final ClutchAnalyzer clutchAnalyzer;
  private final SideWinCalculator sideWinCalculator;

  @Override
  public AnalysisResult analyze(CompleteMatchData rawData) {
    // 1. FUNDAMENT: Ustalenie kto jest w jakim teamie
    MatchTeams teams = teamSideCalculator.calculateTeams(rawData.kills());

    // 2. Analizy niezależne
    var entryFrags = entryAnalyzer.analyze(rawData.kills(), rawData.rounds());
    var tradeKills = tradeAnalyzer.analyze(rawData.kills());

    // 3. Analizy zależne od Teamów (Clutch, SideWins)
    var clutches = clutchAnalyzer.analyze(rawData.kills(), rawData.rounds(), teams);
    var side = sideWinCalculator.analyze(teams, rawData.rounds());

    // 4. Budowanie wyniku
    // Mapujemy nasz internal.model.team na api.dto.TeamInfo (jeśli takie masz w AnalysisResult)
    // Zakładam tu null dla TeamInfo, bo musiałbyś stworzyć mapper, ale logika jest gotowa.
    return new AnalysisResult(
        null, // Mapper: teams.teamA() -> TeamInfo
        null, // Mapper: teams.teamB() -> TeamInfo
        entryFrags,
        clutches,
        tradeKills,
        side);
  }
}
