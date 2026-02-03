package no.krisschaaf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {
    private ScoreBoard scoreBoard;

    @BeforeEach
    void setupTest() {
        this.scoreBoard = new ScoreBoard();
    }

    @Test
    public void shouldCaptureInitialZeroZeroScoreWhenStartingGame() {
        // When
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_1, Continent.NORTH_AMERICA);

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
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_1, Continent.SOUTH_AMERICA);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_2, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_2, Continent.SOUTH_AMERICA);

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
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_1, Continent.SOUTH_AMERICA);
                    this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_1, Continent.SOUTH_AMERICA);
                });

        assertEquals("Game that should be started is already ongoing!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenStartingGameWithEmptyTeamNames() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> this.scoreBoard.startGame("", Continent.SOUTH_AMERICA, "", Continent.SOUTH_AMERICA));

        assertEquals("Missing team name when starting game!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenStartingGameWithNullTeamNames() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> this.scoreBoard.startGame(null, Continent.SOUTH_AMERICA, null, Continent.SOUTH_AMERICA));

        assertEquals("Team names must not be null when starting game!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTeamShouldPlayAgainstThemself() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA));

        assertEquals("Teams cannot play against themselves!", exception.getMessage());
    }

    @Test
    public void shouldRemoveGameFromBoardWhenFinishingGame() {
        // When
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_1, Continent.SOUTH_AMERICA);
        this.scoreBoard.finishGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1);

        // Then
        List<String> summarizedGames = this.scoreBoard.getSummary();
        assertEquals(0, summarizedGames.size());
    }

    @Test
    public void shouldThrowExceptionWhenFinishingGameWithEmptyTeamNames() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> this.scoreBoard.finishGame("", ""));

        assertEquals("Missing team name when finishing game!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenFinishingGameWithNullTeamNames() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> this.scoreBoard.finishGame(null, null));

        assertEquals("Team names must not be null when finishing game!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenFinishingGameIsNotOngoing() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->  this.scoreBoard.finishGame(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1));

        assertEquals("Game that should be finished is not ongoing!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionAndProposeOngoingGameWhenReceivedTeamNamesForFinishingAGameWereInverted() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_1, Continent.SOUTH_AMERICA);
                    this.scoreBoard.finishGame(TestUtils.AWAY_TEAM_NAME_1, TestUtils.HOME_TEAM_NAME_1);
                });

        String message = "Game that should be finished is not ongoing! Did you mean: "
                + TestUtils.HOME_TEAM_NAME_1 + " - " + TestUtils.AWAY_TEAM_NAME_1;

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void shouldUpdateGameScore() {
        // When
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_1, Continent.SOUTH_AMERICA);
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
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->  {
                    this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_1, Continent.SOUTH_AMERICA);
                    this.scoreBoard.updateScore("", "", 0, 0);
                });

        assertEquals("Missing team name when updating score!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingScoreWithIdenticalTeamNames() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> this.scoreBoard.updateScore(
                            TestUtils.HOME_TEAM_NAME_1, TestUtils.HOME_TEAM_NAME_1, 0, 0));

        assertEquals("Teams cannot play against themselves!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingScoreWithNegativeTeamScores() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->  {
                    this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_1, Continent.SOUTH_AMERICA);
                    this.scoreBoard.updateScore(
                            TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, -1, -1);
                });

        assertEquals("Team scores must not be negative when updating score!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingScoresForNotOngoingGame() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->  this.scoreBoard.updateScore(
                            TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, 1, 0));

        assertEquals("Game that should be updated is not ongoing!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionAndProposeOngoingGameWhenReceivedTeamNamesForUpdatingAGameWereInverted() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->  {
                    this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_1, Continent.SOUTH_AMERICA);
                    this.scoreBoard.updateScore(
                            TestUtils.AWAY_TEAM_NAME_1, TestUtils.HOME_TEAM_NAME_1, 1 ,0);
                });

        String message = "Game that should be updated is not ongoing! Did you mean: "
                + TestUtils.HOME_TEAM_NAME_1 + " - " + TestUtils.AWAY_TEAM_NAME_1;

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void shouldSummarizeMultipleGamesWithDifferentTotalScoresInCorrectOrder() {
        // When
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_1, Continent.SOUTH_AMERICA);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_2, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_2, Continent.SOUTH_AMERICA);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_3, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_3, Continent.SOUTH_AMERICA);

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
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_1, Continent.SOUTH_AMERICA);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_2, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_2, Continent.SOUTH_AMERICA);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_3, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_3, Continent.SOUTH_AMERICA);

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
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_1, Continent.SOUTH_AMERICA);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_2, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_2, Continent.SOUTH_AMERICA);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_3, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_3, Continent.SOUTH_AMERICA);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_4, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_4, Continent.SOUTH_AMERICA);

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

    @Test
    public void shouldReturnEmptyListWhenNoGamesAreOngoing() {
        List<String> summarizedContinents = this.scoreBoard.getContinentSummary();

        assertEquals(0, summarizedContinents.size());
    }

    @Test
    public void shouldReturnOneGameWithZeroScores() {
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_2, Continent.EUROPE);
        List<String> summarizedContinents = this.scoreBoard.getContinentSummary();

        assertEquals(2, summarizedContinents.size());
        assertEquals("South America: 0", summarizedContinents.getLast());
        assertEquals("Europe: 0", summarizedContinents.getFirst());
    }

    @Test
    public void shouldReturnMultipleGamesWithUpdateScores() {
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_1, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_1, Continent.EUROPE);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_2, Continent.SOUTH_AMERICA, TestUtils.AWAY_TEAM_NAME_2, Continent.ASIA);
        this.scoreBoard.startGame(TestUtils.HOME_TEAM_NAME_3, Continent.NORTH_AMERICA, TestUtils.AWAY_TEAM_NAME_3, Continent.EUROPE);

        this.scoreBoard.updateScore(TestUtils.HOME_TEAM_NAME_1, TestUtils.AWAY_TEAM_NAME_1, 3, 4);
        this.scoreBoard.updateScore(TestUtils.HOME_TEAM_NAME_2, TestUtils.AWAY_TEAM_NAME_2, 2, 5);
        this.scoreBoard.updateScore(TestUtils.HOME_TEAM_NAME_3, TestUtils.AWAY_TEAM_NAME_3, 1, 2);

        List<String> summarizedContinents = this.scoreBoard.getContinentSummary();

        assertEquals(4, summarizedContinents.size());
        assertEquals("Europe: 6", summarizedContinents.get(0));
        assertEquals("Asia: 5", summarizedContinents.get(1));
        assertEquals("South America: 5", summarizedContinents.get(2));
        assertEquals("North America: 1", summarizedContinents.get(3));
    }
}
