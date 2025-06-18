package com.sportresult.player.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NbaPlayerDto {
    private Long oldId;
    private String firstname;
    private String lastname;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    private String country;
    private int startYear;
    private int proYear;
    private double height;
    private double weight;
    private String college;
    private String affiliation;
    private int jerseyNumber;
    private boolean isActive;
    private String position;
}
