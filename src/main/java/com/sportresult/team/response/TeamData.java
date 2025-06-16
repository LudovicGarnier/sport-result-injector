package com.sportresult.team.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamData {
    private Long id;
    private String name;
    private String nickname;
    private String code;
    private String city;
    private String logo;
    private Boolean allStar;
    private Boolean nbaFranchise;
    private LeaguesData leagues;
}
