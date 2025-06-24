package com.sportresult.statsistics.repository;

import com.sportresult.statsistics.entity.NbaTeamGameStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NbaTeamGameStatisticInjectorRepository extends JpaRepository<NbaTeamGameStatisticsEntity, Integer> {
}
