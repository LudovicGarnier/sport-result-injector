package com.sportresult.game.response;

import com.sportresult.player.response.Parameters;
import com.sportresult.player.response.PlayerData;
import lombok.Data;

import java.util.List;

@Data
public class NbaGameResponse {

    private String get;
    private Parameters parameters;
    private List<Object> errors;
    private int results;
    private List<GameData> response;
}
