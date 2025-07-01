package com.sportresult.statsistics.repository;

import com.sportresult.statsistics.entity.NbaPlayerGameStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NbaPlayerGameStatisticsInjectorRepository  extends JpaRepository<NbaPlayerGameStatisticsEntity, UUID> {
}
