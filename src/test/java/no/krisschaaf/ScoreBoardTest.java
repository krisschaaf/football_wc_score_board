package no.krisschaaf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreBoardTest {
    private ScoreBoard scoreBoard;

    @BeforeEach
    void setupTest() {
        this.scoreBoard = new ScoreBoard();
    }

    @Test
    public void shouldCaptureInitialZeroZeroScoreWhenStartingGame() {
        // When
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME, TestUtils.AWAY_TEAM_NAME);
        List<String> summarizedGames = this.scoreBoard.getSummary();

        // Then
        assertEquals(1, summarizedGames.size());
        assertEquals("Mexico - Canada: 0 - 0", summarizedGames.getFirst());
    }
}
