package com.sportresult.player.repository;

import com.sportresult.player.entity.NbaPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface NbaPlayerInjectorRepository extends JpaRepository<NbaPlayerEntity, UUID> {

    @Query("SELECT s.oldId FROM NbaPlayerEntity s WHERE s.oldId IN :oldIds")
    Set<Long> findByOldId(@Param("oldIds") Collection<Long> id);

    Optional<NbaPlayerEntity> findByOldId(Long id);
}
