package com.sportresult.arena.dto;

import jakarta.persistence.Column;

public record NbaArenaDto(
        String name,
        String city,
        String state,
        String country) {
}
