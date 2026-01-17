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
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);

        // Then
        List<String> summarizedGames = this.scoreBoard.getSummary();
        assertEquals(1, summarizedGames.size());

        String message = TestUtils.buildMessage(
                TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, 0, 0);
        assertEquals(message, summarizedGames.getFirst());

    }

    @Test
    public void shouldCaptureInitialZeroZeroScoreWhenStartingMultipleGames() {
        // When
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_2, TestUtils.AWAY_TEAM_NAME_2);

        // Then
        List<String> summarizedGames = this.scoreBoard.getSummary();
        assertEquals(2, summarizedGames.size());

        String message = TestUtils.buildMessage(
                TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, 0, 0);
        assertEquals(message, summarizedGames.getLast());

        String message2 = TestUtils.buildMessage(
                TestUtils.HOME_TEAM_NAME_2, TestUtils.AWAY_TEAM_NAME_2, 0, 0);
        assertEquals(message2, summarizedGames.getFirst());
    }

    @Test
    public void shouldThrowExceptionWhenToBeStartedGameIsAlreadyOngoing() {
        // Given
        Exception exception = null;

        // When
        try {
            this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);
            this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
        assertEquals("Game that should be started is already ongoing!", exception.getMessage());
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
            this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.HOME_TEAM_NAME_1);
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
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);
        this.scoreBoard.finishGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);

        // Then
        List<String> summarizedGames = this.scoreBoard.getSummary();
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
            this.scoreBoard.finishGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);
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
            this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);
            this.scoreBoard.finishGame(TestUtils.AWAY_TEAM_NAME_1, TestUtils.HOME_TEAM_NAME_1);
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);

        String message = "Game that should be finished is not ongoing! Did you mean: "
                + TestUtils.HOME_TEAM_NAME_1 + " - " + TestUtils.AWAY_TEAM_NAME_1;
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTeamShouldFinishAgainstThemself() {
        // Given
        Exception exception = null;

        // When
        try {
            this.scoreBoard.finishGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.HOME_TEAM_NAME_1);
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
        assertEquals("Teams cannot play against themselves!", exception.getMessage());
    }

    @Test
    public void shouldUpdateGameScore() {
        // When
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);
        this.scoreBoard.updateScore(
                TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, 1, 0);

        // Then
        List<String> summarizedGames = this.scoreBoard.getSummary();
        assertEquals(1, summarizedGames.size());

        String message = TestUtils.buildMessage(
                TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, 1, 0);
        assertEquals(message, summarizedGames.getFirst());
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingScoreWithoutTeamNames() {
        // Given
        Exception exception = null;

        // When
        try {
            this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);
            this.scoreBoard.updateScore("", "", 0, 0);
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
        assertEquals("Missing team name when updating score!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingScoreWithIdenticalTeamNames() {
        // Given
        Exception exception = null;

        // When
        try {
            this.scoreBoard.updateScore(
                    TestUtils.HOME_TEAM_NAME_1, TestUtils.HOME_TEAM_NAME_1, 0, 0);
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
        assertEquals("Teams cannot play against themselves!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingScoreWithNegativeTeamScores() {
        // Given
        Exception exception = null;

        // When
        try {
            this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);
            this.scoreBoard.updateScore(
                    TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, -1, -1);
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
        assertEquals("Team scores must not be negative when updating score!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingScoresForNotOngoingGame() {
        // Given
        Exception exception = null;

        // When
        try {
            this.scoreBoard.updateScore(
                    TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, 1, 0);
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
        assertEquals("Game that should be updated is not ongoing!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionAndProposeOngoingGameWhenReceivedTeamNamesForUpdatingAGameWereInverted() {
        // Given
        Exception exception = null;

        // When
        try {
            this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);
            this.scoreBoard.updateScore(
                    TestUtils.AWAY_TEAM_NAME_1, TestUtils.HOME_TEAM_NAME_1, 1 ,0);
        } catch (Exception e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);

        String message = "Game that should be updated is not ongoing! Did you mean: "
                + TestUtils.HOME_TEAM_NAME_1 + " - " + TestUtils.AWAY_TEAM_NAME_1;
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void shouldSummarizeMultipleGamesWithDifferentTotalScoresInCorrectOrder() {
        // When
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_2, TestUtils.AWAY_TEAM_NAME_2);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_3, TestUtils.AWAY_TEAM_NAME_3);

        this.scoreBoard.updateScore(
                TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, 1, 3);
        this.scoreBoard.updateScore(
                TestUtils.HOME_TEAM_NAME_2, TestUtils.AWAY_TEAM_NAME_2, 3, 3);
        this.scoreBoard.updateScore(
                TestUtils.HOME_TEAM_NAME_3, TestUtils.AWAY_TEAM_NAME_3, 2, 1);

        // Then
        List<String> summarizedGames = this.scoreBoard.getSummary();
        assertEquals(3, summarizedGames.size());

        String message = TestUtils.buildMessage(
                TestUtils.HOME_TEAM_NAME_2, TestUtils.AWAY_TEAM_NAME_2, 3, 3);
        assertEquals(message, summarizedGames.get(0));

        String message2 = TestUtils.buildMessage(
                TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, 1, 3);
        assertEquals(message2, summarizedGames.get(1));

        String message3 = TestUtils.buildMessage(
                TestUtils.HOME_TEAM_NAME_3, TestUtils.AWAY_TEAM_NAME_3, 2, 1);
        assertEquals(message3, summarizedGames.get(2));
    }

    @Test
    public void shouldSummarizeMultipleGamesWithIdenticalTotalScoresInCorrectOrder() {
        // When
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_2, TestUtils.AWAY_TEAM_NAME_2);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_3, TestUtils.AWAY_TEAM_NAME_3);

        this.scoreBoard.updateScore(
                TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, 1, 3);
        this.scoreBoard.updateScore(
                TestUtils.HOME_TEAM_NAME_2, TestUtils.AWAY_TEAM_NAME_2, 3, 1);
        this.scoreBoard.updateScore(
                TestUtils.HOME_TEAM_NAME_3, TestUtils.AWAY_TEAM_NAME_3, 2, 2);

        // Then
        List<String> summarizedGames = this.scoreBoard.getSummary();
        assertEquals(3, summarizedGames.size());

        String message = TestUtils.buildMessage(
                TestUtils.HOME_TEAM_NAME_3, TestUtils.AWAY_TEAM_NAME_3, 2, 2);
        assertEquals(message, summarizedGames.get(0));

        String message2 = TestUtils.buildMessage(
                TestUtils.HOME_TEAM_NAME_2, TestUtils.AWAY_TEAM_NAME_2, 3, 1);
        assertEquals(message2, summarizedGames.get(1));

        String message3 = TestUtils.buildMessage(
                TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, 1, 3);
        assertEquals(message3, summarizedGames.get(2));
    }

    @Test
    public void shouldSummarizeMultipleGamesWithIdenticalAndDifferentTotalScoresInCorrectOrder() {
        // When
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_2, TestUtils.AWAY_TEAM_NAME_2);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_3, TestUtils.AWAY_TEAM_NAME_3);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_4, TestUtils.AWAY_TEAM_NAME_4);

        this.scoreBoard.updateScore(
                TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, 1, 3);
        this.scoreBoard.updateScore(
                TestUtils.HOME_TEAM_NAME_2, TestUtils.AWAY_TEAM_NAME_2, 6, 1);
        this.scoreBoard.updateScore(
                TestUtils.HOME_TEAM_NAME_3, TestUtils.AWAY_TEAM_NAME_3, 0, 2);
        this.scoreBoard.updateScore(
                TestUtils.HOME_TEAM_NAME_4, TestUtils.AWAY_TEAM_NAME_4, 3, 1);

        // Then
        List<String> summarizedGames = this.scoreBoard.getSummary();
        assertEquals(4, summarizedGames.size());

        String message = TestUtils.buildMessage(
                TestUtils.HOME_TEAM_NAME_2, TestUtils.AWAY_TEAM_NAME_2, 6, 1);
        assertEquals(message, summarizedGames.get(0));

        String message2 = TestUtils.buildMessage(
                TestUtils.HOME_TEAM_NAME_4, TestUtils.AWAY_TEAM_NAME_4, 3, 1);
        assertEquals(message2, summarizedGames.get(1));

        String message3 = TestUtils.buildMessage(
                TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, 1, 3);
        assertEquals(message3, summarizedGames.get(2));

        String message4 = TestUtils.buildMessage(
                TestUtils.HOME_TEAM_NAME_3, TestUtils.AWAY_TEAM_NAME_3, 0, 2);
        assertEquals(message4, summarizedGames.get(3));
    }
}
