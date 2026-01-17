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

        onGoingGames.put(startedGameKey, startedGame);
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
            if (onGoingGames.containsKey(invertedGameKey)) {
                String message = "Game that should be finished is not ongoing! Did you mean: "
                        + awayTeamName + " - " + homeTeamName;
                throw new IllegalArgumentException(message);
            } else {
                throw new IllegalArgumentException("Game that should be finished is not ongoing!");
            }
        }
    }

    public List<String> getSummary() {
        return onGoingGames.values().stream()
                .map(Game::toString)
                .toList();
    }
}