package com.sportresult.season.controller;

import com.sportresult.client.NbaApiClient;
import com.sportresult.season.dto.NbaSeasonDto;
import com.sportresult.season.response.NbaSeasonResponse;
import com.sportresult.season.service.NbaSeasonInjectorService;
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
public class NbaSeasonInjectorController {

    private final NbaSeasonInjectorService nbaSeasonInjectorService;

    @Autowired
    private NbaApiClient nbaApiClient;

    @GetMapping("/seasons")
    public ResponseEntity<List<NbaSeasonDto>> getSeasonsFromApi() {
        NbaSeasonResponse response = nbaApiClient.getSeasons();

        List<NbaSeasonDto> nbaSeasonDtos =  nbaSeasonInjectorService.injectSeasons(response);

        return ResponseEntity.ok(nbaSeasonDtos);

    }
}
