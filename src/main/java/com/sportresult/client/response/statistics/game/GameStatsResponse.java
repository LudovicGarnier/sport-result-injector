package com.sportresult.client.response.statistics.game;

import com.sportresult.client.response.standings.Parameters;
import com.sportresult.client.response.statistics.team.TeamStatsData;
import lombok.Data;

import java.util.List;

@Data
public class GameStatsResponse {
    private String get;
    private Parameters parameters;
    private List<Object> errors;
    private int results;
    private List<GameStatsData> response;
}
