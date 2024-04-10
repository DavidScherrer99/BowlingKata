package ch.hearc.ig;
import org.mockito.Mockito;

import java.util.Arrays;

public class Game {
    private Frame frames[];
    private int currentFrame = 0;
    private boolean inFrame = false;
    private int numberOfStrikes = 0;

    private final TableauAffichage tableau;


    public Game(TableauAffichage tableau) {
        this.tableau = tableau;
        this.frames = new Frame[10];
        this.tableau.seConnecter();
    }

    public void roll(int pins) {
        if (currentFrame == 10) {
            throw new IllegalStateException("Game is over");
        }
        if (pins < 0 || pins > 10) {
            throw new IllegalArgumentException("Invalid number of pins");
        } else {
            if (inFrame == false) {
                if (frames[currentFrame] == null){
                    frames[currentFrame] = new Frame();
                }

                if (pins == 10 && currentFrame < 9) {
                    numberOfStrikes++;
                    frames[currentFrame].setFirstRoll(pins);
                    if (numberOfStrikes == 1) {
                        tableau.showStrike(TableauAffichage.StrikeSerie.PREMIER);
                    } else if (numberOfStrikes == 2) {
                        tableau.showStrike(TableauAffichage.StrikeSerie.SECOND);
                    } else if (numberOfStrikes > 2) {
                        tableau.showStrike(TableauAffichage.StrikeSerie.TROISIEME_ET_PLUS);
                    }
                    currentFrame++;
                } else if (pins == 10 && currentFrame == 9) {
                    if ((frames[currentFrame].getFirstRoll() + frames[currentFrame].getSecondRoll() == 10 && frames[currentFrame].getSecondRoll() != 0) || frames[currentFrame].getSecondRoll() == 10) {
                        frames[currentFrame].setThirdRoll(pins);
                    }
                    if (frames[currentFrame].getFirstRoll() == 10) {
                        frames[currentFrame].setSecondRoll(pins);
                    }
                    frames[currentFrame].setFirstRoll(pins);
                } else if (currentFrame == 9 && frames[currentFrame].getFirstRoll() + frames[currentFrame].getSecondRoll() == 10){
                    frames[currentFrame].setThirdRoll(pins);
                    currentFrame++;
                } else {
                    frames[currentFrame].setFirstRoll(pins);
                    inFrame = true;
                }
            } else if (inFrame == true && currentFrame < 9) {
                frames[currentFrame].setSecondRoll(pins);
                if (frames[currentFrame].getFirstRoll() + frames[currentFrame].getSecondRoll() == 10) {
                    tableau.showSpare();
                }
                currentFrame++;
                inFrame = false;
            } else if (currentFrame == 9 && inFrame == true) {
                frames[currentFrame].setSecondRoll(pins);
                inFrame = false;
            }

        }


    }

    public int score() {
        int score = 0;
        for (int i = 0; i < 10; i++) {
            if (frames[i].getFirstRoll() == 10) {
                if (i < 7 && frames[i + 1].getFirstRoll() == 10) {
                    score += 10 + frames[i + 1].getFirstRoll() + frames[i + 2].getFirstRoll();
                } else if (i == 7 && frames[i + 1].getFirstRoll() == 10) {
                    score += 10 + frames[i + 1].getFirstRoll() + frames[i + 1].getFirstRoll();
                } else if (i == 8 && frames[i + 1].getFirstRoll() == 10) {
                    score += 10 + frames[i+1].getSecondRoll() + frames[i+1].getThirdRoll();
                } else if (i == 9){
                    score += 10 + frames[i].getSecondRoll() + frames[i].getThirdRoll();
                } else if (i < 7 && frames[i + 1].getFirstRoll() != 10) {
                    score += 10 + frames[i + 1].getFirstRoll() + frames[i + 1].getSecondRoll();
                }
            } else if (frames[i].getFirstRoll() + frames[i].getSecondRoll() == 10 && frames[i].getFirstRoll() != 10) {
                if (i == 9) {
                    score += 10 + frames[i].getThirdRoll();
                } else {
                    score += 10 + frames[i + 1].getFirstRoll();
                }
            } else {
                score += frames[i].getFirstRoll() + frames[i].getSecondRoll();
            }
        }
        return score;
    }

    public void endGame() {
        Mockito.when(tableau.bestScores()).thenReturn(Arrays.asList(280,274,270));
        for (int i = 0; i < tableau.bestScores().size(); i++) {
            if (score() > tableau.bestScores().get(i)) {
                tableau.updateBestScores(score());
                break;
            }

        }

    }
}
