package com.sportresult.team.controller;


import com.sportresult.client.NbaApiClient;
import com.sportresult.season.dto.NbaSeasonDto;
import com.sportresult.season.response.NbaSeasonResponse;
import com.sportresult.season.service.NbaSeasonInjectorService;
import com.sportresult.team.dto.NbaTeamDto;
import com.sportresult.team.response.NbaTeamResponse;
import com.sportresult.team.service.NbaTeamInjectorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
@Tag(name = "Team", description = "")
public class NbaTeamInjectorController {

    private final NbaTeamInjectorService nbaTeamInjectorService;

    @Autowired
    private NbaApiClient nbaApiClient;

    @GetMapping
    public ResponseEntity<List<NbaTeamDto>> getSeasonsFromApi() {
        NbaTeamResponse response = nbaApiClient.getTeams();

        List<NbaTeamDto> nbaTeamDtos = nbaTeamInjectorService.injectTeams(response);

        return ResponseEntity.ok(nbaTeamDtos);

    }
}
