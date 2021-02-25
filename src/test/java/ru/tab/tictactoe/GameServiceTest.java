package ru.tab.tictactoe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;
import ru.tab.tictactoe.model.*;
import ru.tab.tictactoe.repo.GameRepo;
import ru.tab.tictactoe.service.GameService;

@SpringBootTest
public class GameServiceTest {
    @Autowired
    private GameService gameService;

    @MockBean
    private GameRepo gameRepo;

    @Test
    public void createGame() {
        Player player = new Player();
        player.setName("player");
        player.setId(1L);
        player.setUser(new User());

        Assert.notNull(gameService.createGame(player));
    }
}
