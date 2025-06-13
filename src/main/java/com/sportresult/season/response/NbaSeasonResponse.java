package com.sportresult.season.response;

import lombok.Data;

import java.util.List;

@Data
public class NbaSeasonResponse {
    private String get;
    private List<Object> parameters;
    private List<Object> errors;
    private int results;
    private List<Integer> response;

}
