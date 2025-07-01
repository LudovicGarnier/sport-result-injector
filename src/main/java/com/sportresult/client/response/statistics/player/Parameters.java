package com.sportresult.client.response.statistics.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Parameters {
    private String team;
    private String season;
}