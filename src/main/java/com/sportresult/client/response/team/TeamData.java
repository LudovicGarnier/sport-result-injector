package com.sportresult.client.response.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private TeamLeaguesData leagues;
}
