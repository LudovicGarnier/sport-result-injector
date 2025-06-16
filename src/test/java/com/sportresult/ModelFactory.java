package com.sportresult;

import com.sportresult.team.response.LeagueInfo;
import com.sportresult.team.response.LeaguesData;
import com.sportresult.team.response.TeamData;

public class ModelFactory {

    public static TeamData VALID_TEAMDATA = createTeamData(1L,
            "Lakers",
            "Los Angeles Lakers",
            "LAL",
            "Los Angeles",
            "logo-url",
            true,
            false,
            "Western",
            "Pacific");

    public static TeamData NON_NBA_FRANCHISE_TEAM = createTeamData(3L,
            "G-League",
            "G-League Team",
            "GLT",
            "Minor",
            "g-league-logo",
            false,
            false,
            null,
            null);

    public static TeamData ALLSTAR_TEAMDATA = createTeamData(2L,
            "All Stars",
            "NBA All Stars",
            "AST",
            "All Star",
            "allstar-logo",
            true,
            true,
            null,
            null);

    // Méthodes utilitaires pour créer les données de test
    public static TeamData createTeamData(Long id,
                                          String name,
                                          String nickname,
                                          String code,
                                          String city,
                                          String logo,
                                          boolean nbaFranchise,
                                          boolean allStar,
                                          String conference,
                                          String division) {
        TeamData teamData = TeamData.builder()
                .id(id)
                .name(name)
                .nickname(nickname)
                .code(code)
                .city(city)
                .logo(logo)
                .nbaFranchise(nbaFranchise)
                .allStar(allStar)
                .build();

        if (conference != null && division != null) {
            LeagueInfo leagueInfo = LeagueInfo.builder()
                    .division(division)
                    .conference(conference).build();
            LeaguesData leagues = LeaguesData.builder()
                    .leagueInfo(leagueInfo).build();
            teamData.setLeagues(leagues);
        }

        return teamData;
    }
}
