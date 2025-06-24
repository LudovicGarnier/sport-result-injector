package com.sportresult.client.response.standings;

import lombok.Data;

import java.util.List;

@Data
public class NbaStandingsResponse {

    private String get;
    private Parameters parameters;
    private List<Object> errors;
    private int results;
    private List<StandingsData> response;
}
