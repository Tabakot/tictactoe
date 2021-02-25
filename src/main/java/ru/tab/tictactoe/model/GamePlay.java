package ru.tab.tictactoe.model;

import lombok.Data;

@Data
public class GamePlay {
    private Long gameId;
    private TicToe type;
    private Integer coordinateX;
    private Integer coordinateY;

}
