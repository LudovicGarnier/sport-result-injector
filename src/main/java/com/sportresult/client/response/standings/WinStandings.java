package com.sportresult.client.response.standings;

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
public class WinStandings {
    
    private Integer home;
    private Integer away;
    private Integer total;
    private String percentage;
    private Integer lastTen;
}
