package com.sportresult.team.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueInfo {
    private String conference;
    private String division;
}
