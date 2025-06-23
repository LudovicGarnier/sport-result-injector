package com.sportresult.standing.repository;

import com.sportresult.standing.entity.NbaStandingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface NbaStandingsInjectorRepository extends JpaRepository<NbaStandingsEntity, UUID> {

    @Query("SELECT s.seasonStandings.year FROM NbaStandingsEntity s WHERE s.seasonStandings.year IN :oldIds")
    Set<Integer> findBySeasonStandings(@Param("oldIds") Collection<Integer> id);
}
