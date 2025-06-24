package com.sportresult.player.controller;

import com.sportresult.client.NbaApiClient;
import com.sportresult.player.dto.NbaPlayerDto;
import com.sportresult.client.response.player.NbaPlayerResponse;
import com.sportresult.player.service.NbaPlayerInjectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("players")
@RequiredArgsConstructor
@Tag(name = "Player")
public class NbaPlayerInjectorController {

    private final NbaPlayerInjectorService nbaPlayerInjectorService;

    @Autowired
    private NbaApiClient nbaApiClient;

    @Operation(summary = "Retrieve all Players by team and by year from RapidApi and inject them to Database")
    @GetMapping
    public ResponseEntity<List<NbaPlayerDto>> getPlayersPerTeamAndPerYearFromApi(String teamId, String seasonYear) {
        NbaPlayerResponse nbaPlayerResponse = nbaApiClient.getPlayersPerTeamAndSeason(teamId, seasonYear);

        List<NbaPlayerDto> playerDtos = nbaPlayerInjectorService.injectPlayers(nbaPlayerResponse);

        return ResponseEntity.ok(playerDtos);

    }

    @Operation(summary = "Retrieve all Players by team and by year from RapidApi and inject them to Database")
    @GetMapping(value = "/{year}")
    public ResponseEntity<Map<Integer, List<List<NbaPlayerDto>>>> getAllPlayersPerYearFromApi(@PathVariable int year) {
        Map<Integer, List<List<NbaPlayerDto>>> players = nbaPlayerInjectorService.injectAllPlayerPerYear(nbaApiClient, year);
        return ResponseEntity.ok(players);

    }
}
