package com.sportresult.statsistics.repository;

import com.sportresult.statsistics.entity.NbaTeamSeasonStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NbaTeamSeasonStatisticsInjectorRepository extends JpaRepository<NbaTeamSeasonStatisticsEntity, UUID> {
}
