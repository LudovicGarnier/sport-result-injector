package com.sportresult.season.repository;

import com.sportresult.season.entity.NbaSeasonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface NbaSeasonInjectorRepository extends JpaRepository<NbaSeasonEntity, UUID> {
    @Query("SELECT s.year FROM NbaSeasonEntity s WHERE s.year IN :years")
    Set<Integer> findYearsByYearIn(@Param("years") Collection<Integer> years);
}
