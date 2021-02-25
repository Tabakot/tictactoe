package ru.tab.tictactoe.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tab.tictactoe.model.Game;
import ru.tab.tictactoe.model.Player;
import ru.tab.tictactoe.model.TicToe;
import ru.tab.tictactoe.repo.GameRepo;
import ru.tab.tictactoe.util.MapUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/history")
public class HistoryController {
    private final GameRepo gameRepo;

    @GetMapping
    public List<Game> findGames() {
        return gameRepo.findAll();
    }

    @GetMapping("/myGames/{id}")
    public List<Game> getGames(@PathVariable("id") Player player) {
        return gameRepo.findGamesByPlayer1OrPlayer2(player, player);
    }

    @GetMapping("/{id}")
    public Game getOne(@PathVariable("id") Game game) {
        return game;
    }

    @GetMapping("/{id}/{step}")
    public Game getOne(@PathVariable("id") Game game, @PathVariable int step) {
        StringBuilder field = new StringBuilder(game.getField());
        for (int i = 8; i > step; i--) {
            if (game.getSteps()[i] >= 0)
                field.setCharAt(game.getSteps()[i], '-');
        }
        if (step % 2 == 0)
            game.setCurrentStep(TicToe.O);
        else
            game.setCurrentStep(TicToe.X);

        game.setField(field.toString());
        return game;
    }

    @GetMapping("/topPlayers")
    public Map<Player, Integer> topPlayers() {
        List<Game> games = gameRepo.findAll();
        Map<Player, Integer> playerInfo = new HashMap<>();
        for (Game game: games) {
            if (game.getWinner() != null && game.getPlayer2() != null) {
                if (game.getWinner() == TicToe.X) {
                    if (playerInfo.containsKey(game.getPlayer1())) {
                        playerInfo.put(game.getPlayer1(), playerInfo.get(game.getPlayer1()) + 1);
                    } else {
                        playerInfo.put(game.getPlayer1(), 1);
                    }
                } else {
                    if (playerInfo.containsKey(game.getPlayer2())) {
                        playerInfo.put(game.getPlayer2(), playerInfo.get(game.getPlayer2()) + 1);
                    } else {
                        playerInfo.put(game.getPlayer2(), 1);
                    }
                }
            }
        }
        return MapUtil.sortByValue(playerInfo, 10);
    }
}
