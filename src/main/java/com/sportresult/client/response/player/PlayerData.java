package com.sportresult.client.response.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerData {
    private Long id;
    private String firstname;
    private String lastname;
    private Birth birth;
    private NbaCareer nba;
    private Height height;
    private Weight weight;
    private String college;
    private String affiliation;
    private PlayerLeaguesData leaguesData;
}
