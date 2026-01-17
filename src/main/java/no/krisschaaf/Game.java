package no.krisschaaf;

public class Game {
    private final String homeTeamName;
    private final String awayTeamName;

    private int homeTeamScore;
    private int awayTeamScore;

    public Game(String homeTeamName, String awayTeamName) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;

        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
    }

    public void setHomeTeamScore(int score) {
        this.homeTeamScore = score;
    }

    public void setAwayTeamScore(int score) {
        this.awayTeamScore = score;
    }

    @Override
    public String toString() {
        return this.homeTeamName + " " + this.homeTeamScore + " - " + this.awayTeamName + " " + this.awayTeamScore;
    }
}
