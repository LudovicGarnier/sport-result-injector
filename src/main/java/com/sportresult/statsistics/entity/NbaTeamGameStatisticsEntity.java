package com.sportresult.statsistics.entity;

import com.sportresult.game.entity.NbaGameEntity;
import com.sportresult.season.entity.NbaSeasonEntity;
import com.sportresult.statsistics.dto.NbaTeamGameStatisticsDto;
import com.sportresult.team.entity.NbaTeamEntity;
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
@Table(name = "nba_team_game_statistics")
public class NbaTeamGameStatisticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private NbaTeamEntity team;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private NbaGameEntity game;

    @Column(name = "fastBreakPoints")
    private Integer fastBreakPoints;

    @Column(name = "pointsInPaint")
    private Integer pointsInPaint;

    @Column(name = "biggestLead")
    private Integer biggestLead;

    @Column(name = "secondChancePoints")
    private Integer secondChancePoints;

    @Column(name = "pointsOffTurnovers")
    private Integer pointsOffTurnovers;

    @Column(name = "longestRun")
    private Integer longestRun;

    @Column(name = "points")
    private Integer points;

    @Column(name = "fgm")
    private Integer fgm;

    @Column(name = "fga")
    private Integer fga;

    @Column(name = "fgp")
    private Double fgp;

    @Column(name = "ftm")
    private Integer ftm;

    @Column(name = "fta")
    private Integer fta;

    @Column(name = "ftp")
    private Double ftp;

    @Column(name = "tpm")
    private Integer tpm;

    @Column(name = "tpa")
    private Integer tpa;

    @Column(name = "tpp")
    private Double tpp;

    @Column(name = "offReb")
    private Integer offReb;

    @Column(name = "defReb")
    private Integer defReb;

    @Column(name = "totReb")
    private Integer totReb;

    @Column(name = "assists")
    private Integer assists;

    @Column(name = "pFouls")
    private Integer pFouls;

    @Column(name = "steals")
    private Integer steals;

    @Column(name = "turnovers")
    private Integer turnovers;

    @Column(name = "blocks")
    private Integer blocks;

    @Column(name = "plusMinus")
    private Integer plusMinus;

    @Column(name = "min")
    private String min;

    public NbaTeamGameStatisticsDto toDto() {
        return new NbaTeamGameStatisticsDto(
                team.toDto(),
                game.toDto(),
                fastBreakPoints,
                pointsInPaint,
                biggestLead,
                secondChancePoints,
                pointsOffTurnovers,
                longestRun,
                points,
                fgm,
                fga,
                fgp,
                ftm,
                fta,
                ftp,
                tpm,
                tpa,
                tpp,
                offReb,
                defReb,
                totReb,
                assists,
                pFouls,
                steals,
                turnovers,
                blocks,
                plusMinus,
                min
        );
    }

}
