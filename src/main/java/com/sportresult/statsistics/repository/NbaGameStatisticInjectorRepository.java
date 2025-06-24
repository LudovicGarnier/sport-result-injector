package com.sportresult.statsistics.repository;

import com.sportresult.statsistics.entity.NbaGameStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NbaGameStatisticInjectorRepository extends JpaRepository<NbaGameStatisticsEntity, UUID> {

    Optional<NbaGameStatisticsEntity> findByNbaGameEntity_OldId(Long nbaGameEntityOldId);

}
