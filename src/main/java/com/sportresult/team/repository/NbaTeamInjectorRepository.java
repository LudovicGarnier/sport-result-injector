package com.sportresult.team.repository;

import com.sportresult.team.entity.NbaTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface NbaTeamInjectorRepository extends JpaRepository<NbaTeamEntity, UUID> {

    @Query("SELECT s.oldId FROM NbaTeamEntity s WHERE s.oldId IN :oldIds")
    Set<Long> findByOldId(@Param("oldIds") Collection<Long> id);


    Optional<NbaTeamEntity> findByOldId(Long oldId);
}
