package no.krisschaaf;

import java.time.Instant;

public class Game {
    private final String homeTeamName;
    private final String awayTeamName;
    private final Instant createdAt;

    private int homeTeamScore;
    private int awayTeamScore;

    public Game(String homeTeamName, String awayTeamName) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;

        this.homeTeamScore = 0;
        this.awayTeamScore = 0;

        this.createdAt = Instant.now();
    }

    public void setHomeTeamScore(int score) {
        this.homeTeamScore = score;
    }

    public void setAwayTeamScore(int score) {
        this.awayTeamScore = score;
    }

    public int getTotalScore() {
        return this.homeTeamScore + this.awayTeamScore;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return this.homeTeamName + " " + this.homeTeamScore + " - " + this.awayTeamName + " " + this.awayTeamScore;
    }
}
