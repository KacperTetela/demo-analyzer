package demoanalyzer.com.dem.analyzer.internal.logic;

import demoanalyzer.com.dem.analyzer.internal.model.team.MatchTeams;
import demoanalyzer.com.dem.analyzer.internal.model.team.Team;
import demoanalyzer.com.dem.parser.domain.model.raw.Kills;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TeamSideCalculator {

  public MatchTeams calculateTeams(List<Kills> kills) {
    // 1. Zbieramy unikalnych graczy dla każdej ze stron (CT i T)
    // Bierzemy pod uwagę tylko pierwsze 12 rund, żeby uniknąć zamieszania ze zmianą stron
    Set<String> startingCT = new HashSet<>();
    Set<String> startingT = new HashSet<>();

    for (Kills kill : kills) {
      if (kill.roundNum() <= 12) {
        if ("CT".equalsIgnoreCase(kill.attackerSide())) {
          startingCT.add(kill.attackerName());
        } else if ("T".equalsIgnoreCase(kill.attackerSide())) {
          startingT.add(kill.attackerName());
        }

        // Trzeba też sprawdzić ofiary, bo ktoś mógł zginąć nie robiąc killa
        if ("CT".equalsIgnoreCase(kill.victimSide())) {
          startingCT.add(kill.victimName());
        } else if ("T".equalsIgnoreCase(kill.victimSide())) {
          startingT.add(kill.victimName());
        }
      }
    }

    // 2. Tworzymy obiekty team
    // Zakładamy: team A zaczyna jako CT, team B zaczyna jako T (lub odwrotnie, zależy od konwencji)
    Team teamA = new Team("team A", "CT", new ArrayList<>(startingCT));
    Team teamB = new Team("team B", "T", new ArrayList<>(startingT));

    return new MatchTeams(teamA, teamB);
  }
}
