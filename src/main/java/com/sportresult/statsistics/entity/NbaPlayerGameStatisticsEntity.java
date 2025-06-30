package com.sportresult.statsistics.entity;

import com.sportresult.game.entity.NbaGameEntity;
import com.sportresult.player.entity.NbaPlayerEntity;
import com.sportresult.statsistics.dto.NbaPlayerGameStatisticsDto;
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
@Table(name = "nba_player_game_statistic")
public class NbaPlayerGameStatisticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    NbaPlayerEntity player;

    @ManyToOne
    @JoinColumn(name = "nba_team_id")
    NbaTeamEntity nbaTeam;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private NbaGameEntity game;

    @Column(name = "points")
    private Integer points;

    @Column(name = "pos")
    private String pos;

    @Column(name = "min")
    private String min;

    @Column(name = "fgm")
    private Integer fgm;

    @Column(name = "fga")
    private Integer fga;

    @Column(name = "fgp")
    private String fgp;

    @Column(name = "ftm")
    private Integer ftm;

    @Column(name = "fta")
    private Integer fta;

    @Column(name = "ftp")
    private String ftp;

    @Column(name = "tpm")
    private Integer tpm;

    @Column(name = "tpa")
    private Integer tpa;

    @Column(name = "tpp")
    private String tpp;

    @Column(name = "off_reb")
    private Integer offReb;

    @Column(name = "def_reb")
    private Integer defReb;

    @Column(name = "tot_reb")
    private Integer totReb;

    @Column(name = "assists")
    private Integer assists;

    @Column(name = "p_fouls")
    private Integer pFouls;

    @Column(name = "steals")
    private Integer steals;

    @Column(name = "turnovers")
    private Integer turnovers;

    @Column(name = "blocks")
    private Integer blocks;

    @Column(name = "plusMinus")
    private String plusMinus;

    @Column(name = "comment")
    private String comment;

    public NbaPlayerGameStatisticsDto toDto() {
        return new NbaPlayerGameStatisticsDto(
                player.toDto(),
                nbaTeam.toDto(),
                game.toDto(),
                points,
                pos,
                min,
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
                comment
        );
    }
}
