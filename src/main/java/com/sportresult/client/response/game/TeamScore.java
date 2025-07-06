package com.sportresult.client.response.game;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamScore {
    private Integer win;
    private Integer loss;
    private Series series;
    private List<LineScore> linescore;
    private Integer points;
}
