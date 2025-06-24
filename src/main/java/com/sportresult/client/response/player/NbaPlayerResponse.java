package com.sportresult.client.response.player;

import lombok.Data;

import java.util.List;

@Data
public class NbaPlayerResponse {

    private String get;
    private Parameters parameters;
    private List<Object> errors;
    private int results;
    private List<PlayerData> response;

}
