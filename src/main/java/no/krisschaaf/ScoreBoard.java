package no.krisschaaf;

import java.util.*;

public class ScoreBoard {
    private final HashMap<GameKey, Game> onGoingGames;
    private List<String> onGoingGamesSummary;
    private List<String> continentPointsSummary;

    public ScoreBoard() {
        this.onGoingGames = new HashMap<>();
        this.onGoingGamesSummary = new ArrayList<>();
        this.continentPointsSummary = new ArrayList<>();
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

        updateOnGoingGamesSummary();
    }

    public void finishGame(String homeTeamName, String awayTeamName) {
        validateTeamNames(homeTeamName, awayTeamName, "finishing");

        GameKey gameKeyToFinish = new GameKey(homeTeamName, awayTeamName);
        Game game = this.onGoingGames.remove(gameKeyToFinish);

        checkForInvertedOnGoingGames(homeTeamName, awayTeamName, game, "finished");

        updateOnGoingGamesSummary();
    }

    public void updateScore(String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore) {
        validateTeamNames(homeTeamName, awayTeamName, "updating");

        if (homeTeamScore < 0 || awayTeamScore < 0 ) {
            throw new IllegalArgumentException("Team scores must not be negative when updating score!");
        }

        GameKey gameKeyToUpdate = new GameKey(homeTeamName, awayTeamName);
        Game gameToUpdate = this.onGoingGames.get(gameKeyToUpdate);

        checkForInvertedOnGoingGames(homeTeamName, awayTeamName, gameToUpdate, "updated");

        if(homeTeamScore < gameToUpdate.getHomeTeamScore() || awayTeamScore < gameToUpdate.getAwayTeamScore()) {
            throw new IllegalArgumentException("Team scores must be ascending!");
        }

        gameToUpdate.setHomeTeamScore(homeTeamScore);
        gameToUpdate.setAwayTeamScore(awayTeamScore);

        updateOnGoingGamesSummary();
    }

    public List<String> getSummary() {
        return this.onGoingGamesSummary;
    }

    public List<String> getContinentSummary() {
        return this.continentPointsSummary;
    }

    private static void validateTeamNames(String homeTeamName, String awayTeamName, String phase) {
        if (homeTeamName == null || awayTeamName == null) {
            throw new IllegalArgumentException("Team names must not be null when " + phase + " game!");
        }
        if (homeTeamName.isEmpty() || awayTeamName.isEmpty()) {
            throw new IllegalArgumentException("Missing team name when " + phase + " game!");
        }
    }

    private void checkForInvertedOnGoingGames(String homeTeamName, String awayTeamName, Game game, String phase) {
        if (game == null) {
            GameKey invertedGameKey = new GameKey(awayTeamName, homeTeamName);

            String message = this.onGoingGames.containsKey(invertedGameKey)
                    ? "Game that should be " + phase + " is not ongoing! Did you mean: "
                    + awayTeamName + " - " + homeTeamName
                    : "Game that should be " + phase + " is not ongoing!";

            throw new IllegalArgumentException(message);
        }
    }

    private void updateOnGoingGamesSummary() {
        this.onGoingGamesSummary = this.onGoingGames.values().stream()
                .sorted(Comparator
                        .comparingInt(Game::getTotalScore)
                        .thenComparing(Game::getCreatedAt).reversed())
                .map(Game::toString)
                .toList();

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

        this.continentPointsSummary = pointsPerContinent.entrySet().stream()
                .sorted(Comparator
                        .comparingInt((Map.Entry<Continent, Integer> e) -> e.getValue())
                        .reversed()
                        .thenComparing(e -> e.getKey().name()))
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .toList();
    }
}