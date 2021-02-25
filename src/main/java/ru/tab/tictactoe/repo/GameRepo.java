package ru.tab.tictactoe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tab.tictactoe.model.Game;
import ru.tab.tictactoe.model.Player;

import java.util.List;

public interface GameRepo extends JpaRepository<Game, Long> {
    Game findGameByGameId(Long id);
    List<Game> findGamesByPlayer1OrPlayer2(Player player1, Player player2);
}
