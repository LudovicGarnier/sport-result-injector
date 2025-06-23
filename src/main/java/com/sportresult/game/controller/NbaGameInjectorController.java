package com.sportresult.game.controller;

import com.sportresult.client.NbaApiClient;
import com.sportresult.game.dto.NbaGameDto;
import com.sportresult.game.response.NbaGameResponse;
import com.sportresult.game.service.NbaGameInjectorService;
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
@RequestMapping("games")
@RequiredArgsConstructor
@Tag(name = "Game")
public class NbaGameInjectorController {

    @Autowired
    private NbaApiClient nbaApiClient;

    private final NbaGameInjectorService nbaGameInjectorService;

    @Operation(summary = "Retrieve all Games by year from RapidApi and inject them to Database")
    @GetMapping
    public ResponseEntity<List<NbaGameDto>> getGamesPerYearFromApi(String seasonYear) {
        log.info("Games injected by year: {}", seasonYear);
        NbaGameResponse nbaGameResponse = nbaApiClient.getGamesPerSeason(seasonYear);

        List<NbaGameDto> games = nbaGameInjectorService.injectGames(nbaGameResponse);

        return ResponseEntity.ok(games);

    }

    @Operation(summary = "Retrieve all Games by id from RapidApi and inject them to Database")
    @GetMapping("/id")
    public Long getGamesPerIdFromApi(int id) {

        NbaGameResponse nbaGameResponse = nbaApiClient.getGamesPerId(id);

        nbaGameInjectorService.injectGames(nbaGameResponse);

        return null;

    }

}
