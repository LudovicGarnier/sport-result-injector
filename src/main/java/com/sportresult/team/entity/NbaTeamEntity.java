package com.sportresult.team.entity;

import com.sportresult.team.dto.NbaTeamDto;
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
@Table(name = "nba_team")
public class NbaTeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "oldId", unique = true)
    private Long oldId;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "code")
    private String code;

    @Column(name = "city")
    private String city;

    @Column(name = "logo")
    private String logo;

    @Column(name = "allStar")
    private Boolean allStar;

    @Column(name = "nbaFranchise")
    private Boolean nbaFranchise;

    @Column(name = "conference")
    private String conference;

    @Column(name = "division")
    private String division;

    public NbaTeamDto toDto() {
        return new NbaTeamDto(oldId, name, nickname, code, city, logo, allStar, nbaFranchise, conference, division);
    }
}
