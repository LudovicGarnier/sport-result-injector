package com.sportresult.arena.repository;

import com.sportresult.arena.entity.NbaArenaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NbaArenaRepository extends JpaRepository<NbaArenaEntity, UUID> {

    Optional<NbaArenaEntity> getNbaArenaEntitiesByName(String name);
}
