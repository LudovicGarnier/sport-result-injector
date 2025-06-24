package com.sportresult.client.response.team;

import lombok.Data;

import java.util.List;

@Data
public class NbaTeamResponse {
    private String get;
    private List<Object> parameters;
    private List<Object> errors;
    private int results;
    private List<TeamData> response;

}
