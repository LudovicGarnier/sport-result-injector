package com.sportresult.season.service;

import com.sportresult.season.dto.NbaSeasonDto;
import com.sportresult.season.entity.NbaSeasonEntity;
import com.sportresult.season.repository.NbaSeasonInjectorRepository;
import com.sportresult.season.response.NbaSeasonResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NbaSeasonInjectorService {

    private final NbaSeasonInjectorRepository nbaSeasonInjectorRepository;

    public List<NbaSeasonDto> availableSeasons() {
        return nbaSeasonInjectorRepository.findAll().stream().map(NbaSeasonEntity::toDto).collect(Collectors.toList());
    }

    @Transactional
    public List<NbaSeasonDto> injectSeasons(NbaSeasonResponse response) {
        if (response == null || response.getResponse().isEmpty()) {
            return Collections.emptyList();
        }

        // Batch query to find existing years
        Set<Integer> requestedYears = new HashSet<>(response.getResponse());
        Set<Integer> existingYears = nbaSeasonInjectorRepository.findYearsByYearIn(requestedYears);

        // Filter out years that already exist
        List<NbaSeasonEntity> newSeasonEntities = requestedYears.stream()
                .filter(year -> !existingYears.contains(year))
                .map(year -> NbaSeasonEntity.builder()
                        .year(year)
                        .build())
                .collect(Collectors.toList());

        if (!newSeasonEntities.isEmpty()) {
            nbaSeasonInjectorRepository.saveAll(newSeasonEntities);
        }

        // Convert to DTOs
        return newSeasonEntities.stream()
                .map(NbaSeasonEntity::toDto)
                .collect(Collectors.toList());
    }
}
