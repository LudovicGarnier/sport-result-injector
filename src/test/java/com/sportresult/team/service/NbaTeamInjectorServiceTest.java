package com.sportresult.team.service;

import com.sportresult.team.dto.NbaTeamDto;
import com.sportresult.team.entity.NbaTeamEntity;
import com.sportresult.team.repository.NbaTeamInjectorRepository;
import com.sportresult.client.response.team.NbaTeamResponse;
import com.sportresult.client.response.team.TeamData;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.sportresult.ModelFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("NbaTeamInjectorService - injectTeams")
public class NbaTeamInjectorServiceTest {

    @Mock
    NbaTeamInjectorRepository nbaTeamInjectorRepository = mock(NbaTeamInjectorRepository.class);

    @InjectMocks
    NbaTeamInjectorService nbaTeamInjectorService = new NbaTeamInjectorService(nbaTeamInjectorRepository);

    private NbaTeamResponse validResponse;

    @Test
    @DisplayName("Must return empty list when response is null")
    public void shouldReturnEmptyListWhenResponseIsNull() {
        // When
        List<NbaTeamDto> result = nbaTeamInjectorService.injectTeams(null);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Must return empty list when response is empty")
    public void shouldReturnEmptyListWhenResponseIsEmpty() {
        // Given
        NbaTeamResponse emptyResponse = new NbaTeamResponse();
        emptyResponse.setResponse(Collections.emptyList());

        // When
        List<NbaTeamDto> result = nbaTeamInjectorService.injectTeams(emptyResponse);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Must inject only valid NBA teams (no All-Star)")
    public void shouldInjectOnlyValidNbaTeams() {

        validResponse = new NbaTeamResponse();
        validResponse.setResponse(Arrays.asList(VALID_TEAMDATA, ALLSTAR_TEAMDATA, NON_NBA_FRANCHISE_TEAM));
        // Given
        when(nbaTeamInjectorRepository.findByOldId(List.of(1L))).thenReturn(Set.of());
        when(nbaTeamInjectorRepository.saveAll(any())).thenAnswer(invocation -> {
            Collection<NbaTeamEntity> entities = invocation.getArgument(0);
            return new ArrayList<>(entities);
        });

        // When
        List<NbaTeamDto> result = nbaTeamInjectorService.injectTeams(validResponse);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().name()).isEqualTo("Lakers");

    }

    @Test
    @DisplayName("Must not inject already existing teams")
    public void shouldNotInjectExistingTeams() {
        // Given
        NbaTeamEntity existingEntity = NbaTeamEntity.builder().oldId(1L).build();
        when(nbaTeamInjectorRepository.findByOldId(List.of(1L))).thenReturn(Set.of(existingEntity.getOldId()));

        // When
        List<NbaTeamDto> result = nbaTeamInjectorService.injectTeams(validResponse);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Must correctly build NBA teams")
    public void shouldBuildNbaTeamEntityCorrectly() {

        // Given
        when(nbaTeamInjectorRepository.findByOldId(List.of(1L))).thenReturn(Set.of());
        validResponse = new NbaTeamResponse();
        validResponse.setResponse(Collections.singletonList(VALID_TEAMDATA));

        // When
        List<NbaTeamDto> result = nbaTeamInjectorService.injectTeams(validResponse);

        NbaTeamDto nbaTeamDto = result.getFirst();
        // Check Nba Team build
        assertThat(nbaTeamDto.oldId()).isEqualTo(1L);
        assertThat(nbaTeamDto.name()).isEqualTo("Lakers");
        assertThat(nbaTeamDto.nickname()).isEqualTo("Los Angeles Lakers");
        assertThat(nbaTeamDto.code()).isEqualTo("LAL");
        assertThat(nbaTeamDto.city()).isEqualTo("Los Angeles");
        assertThat(nbaTeamDto.logo()).isEqualTo("logo-url");
        assertThat(nbaTeamDto.allStar()).isFalse();
        assertThat(nbaTeamDto.nbaFranchise()).isTrue();
        assertThat(nbaTeamDto.conference()).isEqualTo("Western");
        assertThat(nbaTeamDto.division()).isEqualTo("Pacific");

        // Then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Must deal with null Leagues datas")
    public void shouldHandleNullLeaguesData() {
        // Given
        TeamData teamWithNullLeagues = createTeamData(5L,
                "Test",
                "Test Team",
                "TST",
                "Test City",
                "test-logo",
                true,
                false,
                null,
                null);
        teamWithNullLeagues.setLeagues(null);

        NbaTeamResponse responseWithNullLeagues = new NbaTeamResponse();
        responseWithNullLeagues.setResponse(Collections.singletonList(teamWithNullLeagues));

        when(nbaTeamInjectorRepository.findByOldId(List.of(5L))).thenReturn(Set.of());
        when(nbaTeamInjectorRepository.saveAll(any())).thenAnswer(invocation -> {
            Collection<NbaTeamEntity> entities = invocation.getArgument(0);
            List<NbaTeamEntity> entityList = new ArrayList<>(entities);

            // Check conference and division are null
            NbaTeamEntity entity = entityList.getFirst();
            assertThat(entity.getConference()).isNull();
            assertThat(entity.getDivision()).isNull();

            return entityList;
        });

        // When
        List<NbaTeamDto> result = nbaTeamInjectorService.injectTeams(responseWithNullLeagues);

        // Then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Must filter All-Star teams")
    public void shouldFilterOutAllStarTeams() {

        // Given
        NbaTeamResponse allStarResponse = new NbaTeamResponse();
        allStarResponse.setResponse(Collections.singletonList(ALLSTAR_TEAMDATA));

        // When
        List<NbaTeamDto> result = nbaTeamInjectorService.injectTeams(allStarResponse);

        // Then
        assertThat(result).isEmpty();
        verify(nbaTeamInjectorRepository, never()).saveAll(any());
    }

    @Test
    @DisplayName("Must filter non-NBA teams")
    public void shouldFilterOutNonNbaFranchises() {
        // Given
        NbaTeamResponse nonNbaResponse = new NbaTeamResponse();
        nonNbaResponse.setResponse(Collections.singletonList(NON_NBA_FRANCHISE_TEAM));

        // When
        List<NbaTeamDto> result = nbaTeamInjectorService.injectTeams(nonNbaResponse);

        // Then
        assertThat(result).isEmpty();
        verify(nbaTeamInjectorRepository, never()).saveAll(any());
    }

    @Test
    @DisplayName("Must return empty list when no team match with criterias")
    public void shouldReturnEmptyListWhenNoTeamsMatchCriteria() {

        // Given
        NbaTeamResponse responseWithInvalidTeams = new NbaTeamResponse();
        responseWithInvalidTeams.setResponse(Arrays.asList(ALLSTAR_TEAMDATA, NON_NBA_FRANCHISE_TEAM));

        // When
        List<NbaTeamDto> result = nbaTeamInjectorService.injectTeams(responseWithInvalidTeams);

        // Then
        assertThat(result).isEmpty();
    }


}