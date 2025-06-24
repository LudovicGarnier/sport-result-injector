package com.sportresult.client.response.statistics.team;

import lombok.Data;

import java.util.List;

@Data
public class TeamStatsResponse {
    private String get;
    private Parameters parameters;
    private List<Object> errors;
    private int results;
    private List<TeamStatsData> response;
}
