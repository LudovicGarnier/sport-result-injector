package com.sportresult.standing.controller;

import com.sportresult.client.NbaApiClient;
import com.sportresult.standing.dto.NbaStandingsDto;
import com.sportresult.standing.response.NbaStandingsResponse;
import com.sportresult.standing.service.NbaStandingsInjectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("standings")
@RequiredArgsConstructor
@Tag(name = "Standings")
public class NbaStandingsInjectorController {

    private final NbaStandingsInjectorService nbaStandingsInjectorService;

    @Autowired
    private NbaApiClient nbaApiClient;

    @Operation(summary = "Retrieve all Games by year from RapidApi and inject them to Database")
    @GetMapping
    public ResponseEntity<List<NbaStandingsDto>> getStandingsPerYearFromApi(String seasonYear) {
        log.info("Standings injected by year: {}", seasonYear);
        NbaStandingsResponse response = nbaApiClient.getStandingsPerYear("standard", seasonYear);

        List<NbaStandingsDto> dtoList = nbaStandingsInjectorService.injectStandings(response);

        return ResponseEntity.ok(dtoList);
    }
}
