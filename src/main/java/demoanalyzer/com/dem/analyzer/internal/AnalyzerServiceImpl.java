package demoanalyzer.com.dem.analyzer.internal;

import demoanalyzer.com.dem.analyzer.api.AnalyzerApi;
import demoanalyzer.com.dem.analyzer.api.dto.AnalysisResult;
import demoanalyzer.com.dem.analyzer.internal.logic.*;
import demoanalyzer.com.dem.analyzer.internal.model.MatchTeams;
import demoanalyzer.com.dem.analyzer.internal.model.Team;
import demoanalyzer.com.dem.core.domain.model.team.TeamInfo;
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
    MatchTeams teams = teamSideCalculator.calculateTeams(rawData.kills(), rawData.rounds());

    var entryFrags = entryAnalyzer.analyze(rawData.kills(), rawData.rounds());
    var tradeKills = tradeAnalyzer.analyze(rawData.kills());
    var clutches = clutchAnalyzer.analyze(rawData.kills(), rawData.rounds(), teams);
    var side = sideWinCalculator.analyze(teams, rawData.rounds());

    // TODO: Tutaj musisz pobrać prawdziwy wynik meczu.
    // Zazwyczaj wynika on z liczby wygranych rund w 'side' lub z headera.
    // Na razie wpisuję 0, żeby kod się kompilował.
    int scoreTeamA = 0;
    int scoreTeamB = 0;

    return new AnalysisResult(
        teamMapper.mapToTeamInfo(teams.teamA()), // Czysto!
        teamMapper.mapToTeamInfo(teams.teamB()), // Czysto!
        entryFrags,
        clutches,
        tradeKills,
        side);
  }
}
