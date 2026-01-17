package no.krisschaaf;

import java.util.LinkedHashMap;
import java.util.List;

public class ScoreBoard {
    private LinkedHashMap<GameKey, Game> onGoingGames;

    public ScoreBoard() {
        this.onGoingGames = new LinkedHashMap<GameKey, Game>();
    }

    public void startGame(String homeTeamName, String awayTeamName) {
        Game startedGame = new Game(homeTeamName, awayTeamName);
        GameKey startedGameKey = new GameKey(homeTeamName, awayTeamName);

        onGoingGames.put(startedGameKey, startedGame);
    }

    public List<String> getSummary() {
        return onGoingGames.values().stream()
                .map(Game::toString)
                .toList();
    }
}