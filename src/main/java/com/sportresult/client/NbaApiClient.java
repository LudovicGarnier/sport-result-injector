package com.sportresult.client;


import com.sportresult.config.NbaApiFeignClientConfig;
import com.sportresult.season.response.NbaSeasonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "api-nba-v1", url = "https://api-nba-v1.p.rapidapi.com/",
        configuration = NbaApiFeignClientConfig.class)
public interface NbaApiClient {

    @GetMapping("/seasons/")
    NbaSeasonResponse getSeasons();
}
