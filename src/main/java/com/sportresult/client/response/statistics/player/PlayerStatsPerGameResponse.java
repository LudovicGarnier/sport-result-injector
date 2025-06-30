package com.sportresult.client.response.statistics.player;

import com.sportresult.client.response.statistics.team.Parameters;
import lombok.Data;

import java.util.List;

@Data
public class PlayerStatsPerGameResponse {
    private String get;
    private Parameters parameters;
    private List<Object> errors;
    private int results;
    private List<PlayerStatsPerGameData> response;
}
