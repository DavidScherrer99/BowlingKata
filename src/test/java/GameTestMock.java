import ch.hearc.ig.Game;
import ch.hearc.ig.TableauAffichage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GameTestMock {

    private TableauAffichage tableau = mock(TableauAffichage.class);
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(tableau);
    }

    @Test
    @DisplayName("Test de connection")
    public void Game_GivenGameConnection_TestSeConnecter() {
        verify(tableau, times(1)).seConnecter();
    }

    @Test
    @DisplayName("Test de spare")
    public void Game_GivenGameSpare_TestShowSpare() {
        game.roll(5);
        game.roll(5);
        verify(tableau, times(1)).showSpare();
    }

    @Test
    @DisplayName("Test de premier strike")
    public void Game_GivenGameFirstStrike_TestShowStrike() {
        game.roll(10);
        verify(tableau, times(1)).showStrike(TableauAffichage.StrikeSerie.PREMIER);
    }

    @Test
    @DisplayName("Test de second strike")
    public void Game_GivenGameSecondStrike_TestShowStrike() {
        game.roll(10);
        game.roll(10);
        verify(tableau, times(1)).showStrike(TableauAffichage.StrikeSerie.SECOND);
    }

    @Test
    @DisplayName("Test de troisième strike")
    public void Game_GivenGameThirdStrike_TestShowStrike() {
        game.roll(10);
        game.roll(10);
        game.roll(10);
        verify(tableau).showStrike(TableauAffichage.StrikeSerie.TROISIEME_ET_PLUS);
    }

    @Test
    @DisplayName("Test de best score")
    public void Game_GivenGameBestScore_TestUpdateBestScores() {
        for (int i = 0; i < 12; i++) {
            game.roll(10);
        }
        game.endGame();
        verify(tableau).updateBestScores(game.score());

    }




}
