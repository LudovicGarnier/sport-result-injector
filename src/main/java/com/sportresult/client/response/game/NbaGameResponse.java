package com.sportresult.client.response.game;

import lombok.Data;

import java.util.List;

@Data
public class NbaGameResponse {

    private String get;
    private Parameters parameters;
    private List<Object> errors;
    private int results;
    private List<GameData> response;
}
