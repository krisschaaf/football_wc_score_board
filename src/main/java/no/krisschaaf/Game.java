package no.krisschaaf;

import java.time.Instant;

class Game {
    private final String homeTeamName;
    private final String awayTeamName;

    private final Continent homeTeamContinent;
    private final Continent awayTeamContinent;

    private int homeTeamScore;
    private int awayTeamScore;

    private final Instant createdAt;

    public Game(String homeTeamName,
                Continent homeTeamContinent,
                String awayTeamName,
                Continent awayTeamContinent) {
        this.homeTeamName = homeTeamName;
        this.homeTeamContinent = homeTeamContinent;

        this.awayTeamName = awayTeamName;
        this.awayTeamContinent = awayTeamContinent;

        this.homeTeamScore = 0;
        this.awayTeamScore = 0;

        this.createdAt = Instant.now();
    }

    public Continent getHomeTeamContinent() {
        return this.homeTeamContinent;
    }
    public Continent getAwayTeamContinent() { return this.awayTeamContinent; }

    public int getHomeTeamScore() { return this.homeTeamScore; }
    public int getAwayTeamScore() { return this.awayTeamScore; }

    public int getTotalScore() { return this.homeTeamScore + this.awayTeamScore; }

    public Instant getCreatedAt() { return this.createdAt; }

    public void setHomeTeamScore(int score) { this.homeTeamScore = score; }
    public void setAwayTeamScore(int score) {
        this.awayTeamScore = score;
    }

    public boolean hasTeam(String teamName) {
        return this.homeTeamName.equals(teamName) || this.awayTeamName.equals(teamName);
    }

    @Override
    public String toString() {
        return this.homeTeamName + " " + this.homeTeamScore + " - " + this.awayTeamName + " " + this.awayTeamScore;
    }
}
