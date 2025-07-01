package com.sportresult.statsistics.repository;

import com.sportresult.statsistics.entity.NbaTeamGameStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NbaTeamGameStatisticsInjectorRepository extends JpaRepository<NbaTeamGameStatisticsEntity, Integer> {
}
