package com.sportresult.season.controller;

import com.sportresult.client.NbaApiClient;
import com.sportresult.season.dto.NbaSeasonDto;
import com.sportresult.client.response.season.NbaSeasonResponse;
import com.sportresult.season.service.NbaSeasonInjectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seasons")
@RequiredArgsConstructor
@Tag(name = "Season")
public class NbaSeasonInjectorController {

    private final NbaSeasonInjectorService nbaSeasonInjectorService;

    @Autowired
    private NbaApiClient nbaApiClient;

    @Operation(summary = "Retrieve all Seasons from RapidApi and inject them to Database")
    @GetMapping
    public ResponseEntity<List<NbaSeasonDto>> getSeasonsFromApi() {
        NbaSeasonResponse response = nbaApiClient.getSeasons();

        List<NbaSeasonDto> nbaSeasonDtos = nbaSeasonInjectorService.injectSeasons(response);

        return ResponseEntity.ok(nbaSeasonDtos);

    }
}
