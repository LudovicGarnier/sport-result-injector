package com.sportresult.season.entity;

import com.sportresult.season.dto.NbaSeasonDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "nba_season")
public class NbaSeasonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "season_year", unique = true)
    private int year;

    public NbaSeasonDto toDto() {
        return new NbaSeasonDto(year);
    }
}
