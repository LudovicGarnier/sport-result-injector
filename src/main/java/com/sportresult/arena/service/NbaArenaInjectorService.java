package com.sportresult.arena.service;


import com.sportresult.arena.entity.NbaArenaEntity;
import com.sportresult.arena.repository.NbaArenaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NbaArenaInjectorService {

    private final NbaArenaRepository nbaArenaRepository;

    public Optional<NbaArenaEntity> getNbaArenaEntitiesByName(String name) {
        return nbaArenaRepository.getNbaArenaEntitiesByName(name);
    }

    public NbaArenaEntity save(NbaArenaEntity nbaArenaEntity) {
        return nbaArenaRepository.saveAndFlush(nbaArenaEntity);
    }
}
