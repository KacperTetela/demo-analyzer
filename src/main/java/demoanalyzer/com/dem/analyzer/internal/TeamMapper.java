package demoanalyzer.com.dem.analyzer.internal;

import demoanalyzer.com.dem.analyzer.internal.model.Team;
import demoanalyzer.com.dem.core.domain.model.team.TeamInfo;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TeamMapper {

    // Metoda przyjmuje teraz TYLKO Team internalTeam
    public TeamInfo mapToTeamInfo(Team internalTeam) {
        List<String> p = internalTeam.namesOfPlayers();

        return new TeamInfo(
                getPlayer(p, 0),
                getPlayer(p, 1),
                getPlayer(p, 2),
                getPlayer(p, 3),
                getPlayer(p, 4),
                internalTeam.score() // <--- Pobieramy wynik bezpośrednio z Teamu
        );
    }

    private String getPlayer(List<String> players, int index) {
        if (players != null && players.size() > index) {
            return players.get(index);
        }
        return "Bot/Unknown";
    }
}