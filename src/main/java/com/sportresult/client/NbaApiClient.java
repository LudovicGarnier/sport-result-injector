package com.sportresult.client;


import com.sportresult.config.NbaApiFeignClientConfig;
import com.sportresult.player.response.NbaPlayerResponse;
import com.sportresult.season.response.NbaSeasonResponse;
import com.sportresult.team.response.NbaTeamResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-nba-v1", url = "https://api-nba-v1.p.rapidapi.com/",
        configuration = NbaApiFeignClientConfig.class)
public interface NbaApiClient {

    @GetMapping("/seasons/")
    NbaSeasonResponse getSeasons();

    @GetMapping("/teams/")
    NbaTeamResponse getTeams();

    @GetMapping("/players?team={teamId}&season={seasonYear}")
    NbaPlayerResponse getPlayersPerTeamAndSeason(@PathVariable String teamId, @PathVariable String seasonYear);
}
