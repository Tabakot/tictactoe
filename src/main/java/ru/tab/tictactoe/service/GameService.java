package ru.tab.tictactoe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tab.tictactoe.exception.IllegalIndexException;
import ru.tab.tictactoe.exception.InvalidGameException;
import ru.tab.tictactoe.exception.InvalidTypeException;
import ru.tab.tictactoe.model.*;
import ru.tab.tictactoe.repo.GameRepo;

import java.util.Arrays;
import java.util.Random;

@Service
public class GameService {
    private final GameRepo gameRepo;

    @Autowired
    public GameService(GameRepo gameRepo) {
        this.gameRepo = gameRepo;
    }

    public Game createGame(Player player) {
        Game game = new Game();
        game.setField("---------");
        game.setPlayer1(player);
        game.setCurrentStep(TicToe.X);
        game.setStatus(GameStatus.NEW);
        int[] arr = new int[9];
        Arrays.fill(arr, -1);
        game.setSteps(arr);
        gameRepo.save(game);
        return game;
    }

    public Game connectToGame(Player player, Long gameId) throws InvalidGameException {
        Game game = gameRepo.findGameByGameId(gameId);
        System.out.println(game);
        if (game.getPlayer2() != null) {
            throw new InvalidGameException("Game is not valid");
        }
        game.setPlayer2(player);
        game.setStatus(GameStatus.IN_PROGRESS);
        gameRepo.save(game);
        return game;
    }

    public Game gamePlay(GamePlay gamePlay) throws InvalidTypeException, IllegalIndexException {
        Game game = gameRepo.findGameByGameId(gamePlay.getGameId());
        game.setStatus(GameStatus.IN_PROGRESS);
        if (game.getCurrentStep() != gamePlay.getType()) {
            throw new InvalidTypeException("Type is not valid");
        }
        StringBuilder field = new StringBuilder(game.getField());
        int index = gamePlay.getCoordinateX() + gamePlay.getCoordinateY() * 3;
        if (field.charAt(index) == TicToe.X.getValue()
                || field.charAt(index) == TicToe.O.getValue()) {
            throw new IllegalIndexException("TicToe is already exist");
        }
        field.setCharAt(index, gamePlay.getType().getValue());

        if (game.getCurrentStep() == TicToe.X)
            game.setCurrentStep(TicToe.O);
        else
            game.setCurrentStep(TicToe.X);

        if (checkWinner(field, gamePlay.getType())) {
            game.setWinner(gamePlay.getType());
            game.setStatus(GameStatus.FINISH);
        }

        game.setField(field.toString());
        int[] gameSteps = game.getSteps();
        gameSteps[countTicToe(field)] = index;
        game.setSteps(gameSteps);

        if (game.getCurrentStep() == TicToe.O
                && game.getPlayer2() == null
                && game.getStatus() == GameStatus.IN_PROGRESS) {
            game = ticTacToeBot(game);
        }
        gameRepo.save(game);
        return game;
    }

    private int countTicToe(StringBuilder field) {
        int counter = -1;
        for (int i = 0; i < field.length(); i++) {
            if (field.charAt(i) == TicToe.X.getValue()
                    || field.charAt(i) == TicToe.O.getValue()) {
                counter++;
            }
        }
        return counter;
    }

    private Boolean checkWinner(StringBuilder field, TicToe ticToe) {
        if (field.charAt(0) == ticToe.getValue()
                && field.charAt(4) == ticToe.getValue()
                && field.charAt(8) == ticToe.getValue()
                || field.charAt(2) == ticToe.getValue()
                && field.charAt(4) == ticToe.getValue()
                && field.charAt(6) == ticToe.getValue())
            return true;
        for (int i = 0; i < 3; i++) {
            int counter = 0;
            for (int j = 0; j < 3; j++) {
                if (field.charAt(i + j * 3) == ticToe.getValue()) {
                    counter++;
                }
            }
            if (counter == 3) {
                return true;
            }
            counter = 0;
            for (int j = 0; j < 3; j++) {
                if (field.charAt(i * 3 + j) == ticToe.getValue()) {
                    counter++;
                }
            }
            if (counter == 3) {
                return true;
            }
        }
        return false;
    }

    private Game ticTacToeBot(Game game) {
        StringBuilder field = new StringBuilder(game.getField());
        game.setCurrentStep(TicToe.X);
        int[] gameSteps = game.getSteps();
        for (int i = 0; i < 9; i++) {
            if (field.charAt(i) == '-') {
                field.setCharAt(i, TicToe.O.getValue());
                if (checkWinner(field, TicToe.O)) {
                    field.setCharAt(i, TicToe.O.getValue());
                    game.setField(field.toString());
                    game.setWinner(TicToe.O);
                    game.setStatus(GameStatus.FINISH);
                    gameSteps[countTicToe(field)] = i;
                    game.setSteps(gameSteps);
                    return game;
                }
                field.setCharAt(i, '-');
            }
        }
        for (int i = 0; i < 9; i++) {
            if (field.charAt(i) == '-') {
                field.setCharAt(i, TicToe.X.getValue());
                if (checkWinner(field, TicToe.X)) {
                    field.setCharAt(i, TicToe.O.getValue());
                    game.setField(field.toString());
                    gameSteps[countTicToe(field)] = i;
                    game.setSteps(gameSteps);
                    return game;
                }
                field.setCharAt(i, '-');
            }
        }
        Random rnd = new Random();
        int coordinate;
        do {
            coordinate = rnd.nextInt(9);
        } while (field.charAt(coordinate) != '-');
        field.setCharAt(coordinate, TicToe.O.getValue());
        game.setField(field.toString());
        gameSteps[countTicToe(field)] = coordinate;
        game.setSteps(gameSteps);
        return game;
    }
}
