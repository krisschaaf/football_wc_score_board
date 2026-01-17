package no.krisschaaf;

import java.util.LinkedHashMap;
import java.util.List;

public class ScoreBoard {
    private final LinkedHashMap<GameKey, Game> onGoingGames;

    public ScoreBoard() {
        this.onGoingGames = new LinkedHashMap<>();
    }

    public void startGame(String homeTeamName, String awayTeamName) {
        if (homeTeamName.isEmpty() || awayTeamName.isEmpty()) {
            throw new IllegalArgumentException("Missing team name when starting game!");
        }
        if (homeTeamName.equals(awayTeamName)) {
            throw new IllegalArgumentException("Teams cannot play against themselves!");
        }

        Game startedGame = new Game(homeTeamName, awayTeamName);
        GameKey startedGameKey = new GameKey(homeTeamName, awayTeamName);

        Game game = onGoingGames.put(startedGameKey, startedGame);

        if (game != null) {
            throw new IllegalArgumentException("Game that should be started is already ongoing!");
        }
    }

    public void finishGame(String homeTeamName, String awayTeamName) {
        if (homeTeamName.isEmpty() || awayTeamName.isEmpty()) {
            throw new IllegalArgumentException("Missing team name when finishing game!");
        }
        if (homeTeamName.equals(awayTeamName)) {
            throw new IllegalArgumentException("Teams cannot play against themselves!");
        }

        GameKey finishedGameKey = new GameKey(homeTeamName, awayTeamName);
        Game game = this.onGoingGames.remove(finishedGameKey);

        if (game == null) {
            GameKey invertedGameKey = new GameKey(awayTeamName, homeTeamName);

            String message = onGoingGames.containsKey(invertedGameKey)
                    ? "Game that should be finished is not ongoing! Did you mean: "
                            + awayTeamName + " - " + homeTeamName
                    : "Game that should be finished is not ongoing!";

            throw new IllegalArgumentException(message);
        }
    }

    public void updateScore(String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore) {
        if (homeTeamName.isEmpty() || awayTeamName.isEmpty()) {
            throw new IllegalArgumentException("Missing team name when updating score!");
        }
        if (homeTeamName.equals(awayTeamName)) {
            throw new IllegalArgumentException("Teams cannot play against themselves!");
        }
        if (homeTeamScore < 0 || awayTeamScore < 0 ) {
            throw new IllegalArgumentException("Team scores must not be negative when updating score!");
        }

        GameKey gameKeyToUpdate = new GameKey(homeTeamName, awayTeamName);
        Game gameToUpdate = this.onGoingGames.get(gameKeyToUpdate);

        if (gameToUpdate == null) {
            GameKey invertedGameKey = new GameKey(awayTeamName, homeTeamName);

            String message = onGoingGames.containsKey(invertedGameKey)
                    ? "Game that should be updated is not ongoing! Did you mean: "
                    + awayTeamName + " - " + homeTeamName
                    : "Game that should be updated is not ongoing!";

            throw new IllegalArgumentException(message);
        }

        gameToUpdate.setHomeTeamScore(homeTeamScore);
        gameToUpdate.setAwayTeamScore(awayTeamScore);
    }

    public List<String> getSummary() {
        return onGoingGames.values().stream()
                .sorted((g1, g2) -> {
                    int comp = g2.getTotalScore() - g1.getTotalScore();
                    if (comp == 0) {
                        return g2.getCreatedAt().compareTo(g1.getCreatedAt());
                    }
                    return comp;
                })
                .map(Game::toString)
                .toList();
    }
}