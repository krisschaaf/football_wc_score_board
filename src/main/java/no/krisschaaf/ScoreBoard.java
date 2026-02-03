package no.krisschaaf;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreBoard {
    private final HashMap<GameKey, Game> onGoingGames;

    public ScoreBoard() {
        this.onGoingGames = new HashMap<>();
    }

    public void startGame(String homeTeamName,
                          Continent homeTeamContinent,
                          String awayTeamName,
                          Continent awayTeamContinent) {
        validateTeamNames(homeTeamName, awayTeamName, "starting");

        if (homeTeamName.equals(awayTeamName)) {
            throw new IllegalArgumentException("Teams cannot play against themselves!");
        }

        GameKey startedGameKey = new GameKey(homeTeamName, awayTeamName);
        Game startedGame = new Game(homeTeamName, homeTeamContinent, awayTeamName, awayTeamContinent);

        Game game = onGoingGames.put(startedGameKey, startedGame);

        if (game != null) {
            throw new IllegalArgumentException("Game that should be started is already ongoing!");
        }
    }

    public void finishGame(String homeTeamName, String awayTeamName) {
        validateTeamNames(homeTeamName, awayTeamName, "finishing");

        GameKey gameKeyToFinish = new GameKey(homeTeamName, awayTeamName);
        Game game = this.onGoingGames.remove(gameKeyToFinish);

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
        validateTeamNames(homeTeamName, awayTeamName, "updating");

        if (homeTeamScore < 0 || awayTeamScore < 0 ) { // Scores can go backwards
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
                .sorted(Comparator
                        .comparingInt(Game::getTotalScore)
                        .thenComparing(Game::getCreatedAt).reversed())
                .map(Game::toString)
                .toList();
    }

    public List<String> getContinentSummary() {
        HashMap<Continent, Integer> pointsPerContinent = new HashMap<>();

        this.onGoingGames.values().forEach(game -> {
            pointsPerContinent.merge(
                    game.getHomeTeamContinent(),
                    game.getHomeTeamScore(),
                    Integer::sum
            );

            pointsPerContinent.merge(
                    game.getAwayTeamContinent(),
                    game.getAwayTeamScore(),
                    Integer::sum
            );
        });

        return pointsPerContinent.entrySet().stream()
                .sorted(Comparator
                        .comparingInt((Map.Entry<Continent, Integer> e) -> e.getValue())
                        .reversed()
                        .thenComparing(e -> e.getKey().name()))
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .toList();
    }

    private static void validateTeamNames(String homeTeamName, String awayTeamName, String phase) {
        if (homeTeamName == null || awayTeamName == null) {
            throw new IllegalArgumentException("Team names must not be null when " + phase + " game!");
        }
        if (homeTeamName.isEmpty() || awayTeamName.isEmpty()) {
            throw new IllegalArgumentException("Missing team name when " + phase + " game!");
        }
    }
}