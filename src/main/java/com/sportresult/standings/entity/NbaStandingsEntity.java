package com.sportresult.standings.entity;

import com.sportresult.season.entity.NbaSeasonEntity;
import com.sportresult.standings.dto.NbaStandingsDto;
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
@Table(name = "nba_standings")
public class NbaStandingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_standings")
    private NbaSeasonEntity seasonStandings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_standings")
    private NbaTeamEntity teamStandings;

    @Column(name = "conferenceName")
    private String conferenceName;

    @Column(name = "conferenceRank")
    private Integer conferenceRank;

    @Column(name = "conferenceWin")
    private Integer conferenceWin;

    @Column(name = "conferenceLoss")
    private Integer conferenceLoss;

    @Column(name = "divisionName")
    private String divisionName;

    @Column(name = "divisionRank")
    private Integer divisionRank;

    @Column(name = "divisionWin")
    private Integer divisionWin;

    @Column(name = "divisionLoss")
    private Integer divisionLoss;

    @Column(name = "winHome")
    private Integer winHome;

    @Column(name = "winAway")
    private Integer winAway;

    @Column(name = "winTotal")
    private Integer winTotal;

    @Column(name = "winPercentage")
    private String winPercentage;

    @Column(name = "lastTenWin")
    private Integer lastTenWin;

    @Column(name = "lossHome")
    private Integer lossHome;

    @Column(name = "lossAway")
    private Integer lossAway;

    @Column(name = "lossTotal")
    private Integer lossTotal;

    @Column(name = "lossPercentage")
    private String lossPercentage;

    @Column(name = "lastTenLoss")
    private Integer lastTenLoss;

    @Column(name = "gameBehind")
    private String gameBehind;

    @Column(name = "streak")
    private Integer streak;

    public NbaStandingsDto toDto() {
        return new NbaStandingsDto(
                seasonStandings.toDto(),
                teamStandings.toDto(),
                conferenceName,
                conferenceRank,
                conferenceWin,
                conferenceLoss,
                divisionName,
                divisionRank,
                divisionWin,
                divisionLoss,
                winHome,
                winAway,
                winTotal,
                winPercentage,
                lastTenWin,
                lossHome,
                lossAway,
                lossTotal,
                lossPercentage,
                lastTenLoss,
                gameBehind,
                streak
                );
    }
}
