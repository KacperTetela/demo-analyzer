package demoanalyzer.com.domain.analyzer;

public record GameDetailsDTO(String mapName, String serverName, Team teamA, Team teamB) {
    public Team getTeamForSide(String side, int roundNum) {
        String sideOfTeamA = teamA.getSideInfo(roundNum);
        return sideOfTeamA.equalsIgnoreCase(side) ? teamA : teamB;
    }
}
