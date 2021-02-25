package ru.tab.tictactoe.controller.dto;

import lombok.Data;
import ru.tab.tictactoe.model.Player;

@Data
public class ConnectRequest {
    private Player player;
    private Long gameId;
}
