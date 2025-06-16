package com.sportresult.team.dto;

public record NbaTeamDto(Long oldId,
                         String name,
                         String nickname,
                         String code,
                         String city,
                         String logo,
                         boolean allStar,
                         boolean nbaFranchise,
                         String conference,
                         String division) {
}
