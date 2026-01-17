package no.krisschaaf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    public void shouldThrowExceptionWhenStartingGameWithEmptyTeamNames() {
        // Given
        Exception exception = null;

        // When
        try {
            this.scoreBoard.startGame("", "");
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
        assertEquals("Missing team name when starting game!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTeamShouldPlayAgainstThemself() {
        // Given
        Exception exception = null;

        // When
        try {
            this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME, TestUtils.HOME_TEAM_NAME);
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
        assertEquals("Teams cannot play against themselves!", exception.getMessage());
    }

    @Test
    public void shouldRemoveGameFromBoardWhenFinishingGame() {
        // When
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME, TestUtils.AWAY_TEAM_NAME);
        this.scoreBoard.finishGame(TestUtils.HOME_TEAM_NAME, TestUtils.AWAY_TEAM_NAME);
        List<String> summarizedGames = this.scoreBoard.getSummary();

        // Then
        assertEquals(0, summarizedGames.size());
    }

    @Test
    public void shouldThrowExceptionWhenFinishingGameWithEmptyTeamNames() {
        // Given
        Exception exception = null;

        // When
        try {
            this.scoreBoard.finishGame("", "");
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
        assertEquals("Missing team name when finishing game!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenFinishingGameIsNotOngoing() {
        // Given
        Exception exception = null;

        // When
        try {
            this.scoreBoard.finishGame(TestUtils.HOME_TEAM_NAME, TestUtils.AWAY_TEAM_NAME);
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
        assertEquals("Game that should be finished is not ongoing!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionAndProposeOngoingGameWhenReceivedTeamNamesForFinishingAGameWereInverted() {
        // Given
        Exception exception = null;

        // When
        try {
            this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME, TestUtils.AWAY_TEAM_NAME);
            this.scoreBoard.finishGame(TestUtils.AWAY_TEAM_NAME, TestUtils.HOME_TEAM_NAME);
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);

        String message = "Game that should be finished is not ongoing! Did you mean: "
                + TestUtils.HOME_TEAM_NAME + " - " + TestUtils.AWAY_TEAM_NAME;
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTeamShouldFinishAgainstThemself() {
        // Given
        Exception exception = null;

        // When
        try {
            this.scoreBoard.finishGame(TestUtils.HOME_TEAM_NAME, TestUtils.HOME_TEAM_NAME);
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
        assertEquals("Teams cannot play against themselves!", exception.getMessage());
    }
}
