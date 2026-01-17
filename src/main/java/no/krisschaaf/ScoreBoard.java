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

        Game startedGame = new Game(homeTeamName, awayTeamName);
        GameKey startedGameKey = new GameKey(homeTeamName, awayTeamName);

        onGoingGames.put(startedGameKey, startedGame);
    }

    public void finishGame(String homeTeamName, String awayTeamName) {
        if (homeTeamName.isEmpty() || awayTeamName.isEmpty()) {
            throw new IllegalArgumentException("Missing team name when finishing game!");
        }

        GameKey finishedGameKey = new GameKey(homeTeamName, awayTeamName);
        Game game = this.onGoingGames.remove(finishedGameKey);

        if (game == null) {
            throw new IllegalArgumentException("Game that should be finished is not ongoing!");
        }
    }

    public List<String> getSummary() {
        return onGoingGames.values().stream()
                .map(Game::toString)
                .toList();
    }
}