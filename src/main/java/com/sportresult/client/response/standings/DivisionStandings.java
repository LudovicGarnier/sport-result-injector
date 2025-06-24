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
public class DivisionStandings {

    private String name;
    private Integer rank;
    private Integer win;
    private Integer loss;
    private String gameBehind;
}
