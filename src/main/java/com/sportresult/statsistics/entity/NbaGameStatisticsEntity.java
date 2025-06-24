package com.sportresult.statsistics.entity;

import com.sportresult.game.entity.NbaGameEntity;
import com.sportresult.statsistics.dto.NbaGameStatisticsDto;
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
@Table(name = "nba_game_statistic")
public class NbaGameStatisticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private NbaGameEntity nbaGameEntity;

    @ManyToOne
    @JoinColumn(name = "visitor_team_statistics_id")
    NbaTeamGameStatisticsEntity visitorTeamStatisticsEntity;

    @ManyToOne
    @JoinColumn(name = "home_team_statistics_id")
    NbaTeamGameStatisticsEntity homeTeamStatisticsEntity;

    public NbaGameStatisticsDto toDto() {
        return NbaGameStatisticsDto.builder()
                .nbaGame(nbaGameEntity.toDto())
                .homeTeamStatistics(homeTeamStatisticsEntity.toDto())
                .awayTeamStatistics(visitorTeamStatisticsEntity.toDto())
                .build();
    }
}
