package com.sportresult.client;


import com.sportresult.client.response.game.NbaGameResponse;
import com.sportresult.client.response.player.NbaPlayerResponse;
import com.sportresult.client.response.season.NbaSeasonResponse;
import com.sportresult.client.response.standings.NbaStandingsResponse;
import com.sportresult.client.response.statistics.game.GameStatsResponse;
import com.sportresult.client.response.statistics.player.PlayerStatsPerGameResponse;
import com.sportresult.client.response.statistics.team.TeamStatsResponse;
import com.sportresult.client.response.team.NbaTeamResponse;
import com.sportresult.config.NbaApiFeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-nba-v1", url = "https://api-nba-v1.p.rapidapi.com/",
        configuration = NbaApiFeignClientConfig.class)
public interface NbaApiClient {

    /*
    SEASON
     */
    @GetMapping("/seasons/")
    NbaSeasonResponse getSeasons();

    /*
    TEAMS
     */
    @GetMapping("/teams/")
    NbaTeamResponse getTeams();

    /*
    PLAYERS
     */
    @GetMapping("/players?team={teamId}&season={seasonYear}")
    NbaPlayerResponse getPlayersPerTeamAndSeason(@PathVariable String teamId, @PathVariable String seasonYear);

    /*
    GAMES
     */
    @GetMapping("/games?season={seasonYear}")
    NbaGameResponse getGamesPerSeason(@PathVariable String seasonYear);

    @GetMapping("/games?id={id}")
    NbaGameResponse getGamesPerId(@PathVariable String id);

    /*
    STANDINGS
     */
    @GetMapping("/standings?league={league}&season={seasonYear}")
    NbaStandingsResponse getStandingsPerYear(@PathVariable String league, @PathVariable String seasonYear);

    /*
    STATISTICS
     */
    @GetMapping("/games/statistics?id={gameId}")
    GameStatsResponse getStatisticsPerGamesOldId(@PathVariable String gameId);

    @GetMapping("/teams/statistics?id={teamId}&season={season}")
    TeamStatsResponse getStatisticsPerTeamAndPerYear(@PathVariable long teamId, @PathVariable int season);

    @GetMapping("/players/statistics?game={gameId}")
    PlayerStatsPerGameResponse getPlayerGameStatisticsPerGameFromApi(@PathVariable long gameId);

}
