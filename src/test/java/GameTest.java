import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ch.hearc.ig.Game;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {
    @Test
    @DisplayName("20 throws of 0 pin => score: 0")
    public void score_20times0_shouldBe0() {
        Game g = new Game();
        for (int i = 0; i < 20; i++) {
            g.roll(0);
        }
        assert g.score() == 0;
    }

    @Test
    @DisplayName("20 throws of 1 pin => score: 20")
    public void score_20times1_shouldBe20() {
        Game g = new Game();
        for (int i = 0; i < 20; i++) {
            g.roll(1);
        }
        assert g.score() == 20;
    }

    @Test
    @DisplayName("One pair of fives + 3 => score: 16")
    public void score_1pair5_3_shouldBe16() {
        Game g = new Game();
        g.roll(5);
        g.roll(5);
        g.roll(3);
        for (int i = 0; i < 17; i++) {
            g.roll(0);
        }
        assert g.score() == 16;
    }

    @Test
    @DisplayName("One Strike + 3 + 4 => score: 24")
    public void score_1strike3_4_shouldBe24() {
        Game g = new Game();
        g.roll(10);
        g.roll(3);
        g.roll(4);
        for (int i = 0; i < 16; i++) {
            g.roll(0);
        }
        assert g.score() == 24;
    }

    @Test
    @DisplayName("Perfect game => score: 300")
    public void score_perfectGame_shouldBe300() {
        Game g = new Game();
        for (int i = 0; i < 12; i++) {
            g.roll(10);
        }
        assert g.score() == 300;
    }

    @Test
    @DisplayName("Negative pins => IllegalArgumentException")
    public void roll_negativePins_shouldThrowIllegalArgumentException() {
        Game g = new Game();
        try {
            g.roll(-1);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
    }

}
