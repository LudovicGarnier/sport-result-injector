package com.sportresult.player.response;

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
