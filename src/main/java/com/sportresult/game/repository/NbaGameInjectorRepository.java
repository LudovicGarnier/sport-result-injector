package com.sportresult.game.repository;

import com.sportresult.game.entity.NbaGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface NbaGameInjectorRepository extends JpaRepository<NbaGameEntity, UUID> {
    @Query("SELECT s.oldId FROM NbaGameEntity s WHERE s.oldId IN :oldIds")
    Set<Long> findByOldId(@Param("oldIds") Collection<Long> id);
}
