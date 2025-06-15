package com.sportresult.season.service;

import com.sportresult.season.dto.NbaSeasonDto;
import com.sportresult.season.repository.NbaSeasonInjectorRepository;
import com.sportresult.season.response.NbaSeasonResponse;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NbaSeasonInjectorServiceTest {

    @Mock
    NbaSeasonInjectorRepository repository = Mockito.mock(NbaSeasonInjectorRepository.class);

    @Mock
    NbaSeasonInjectorService service = new NbaSeasonInjectorService(repository);

    @InjectMocks
    NbaSeasonResponse nbaSeasonResponse;

    @Test
    @DisplayName("should return a List of NbaSeasonDto")
    public void shouldSaveNbaSeasonDto() {
        nbaSeasonResponse = new NbaSeasonResponse();
        nbaSeasonResponse.setResponse(List.of(2015));
        List<NbaSeasonDto> dtoList = service.injectSeasons(nbaSeasonResponse);
        NbaSeasonDto seasonDto = dtoList.get(0);
        assertEquals(2015, seasonDto.year());
    }

    @Test
    @DisplayName("should return a empty List of NbaSeasonDto if noResponse")
    public void shouldReturnEmptyListIfNoResponse() {
        List<NbaSeasonDto> dtoList = service.injectSeasons(nbaSeasonResponse);
        assertEquals(0, dtoList.size());
    }

}