package no.krisschaaf;

public class Game {
    private String homeTeamName;
    private String awayTeamName;

    private int homeTeamScore;
    private int awayTeamScore;

    public Game(String homeTeamName, String awayTeamName) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;

        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
    }

    @Override
    public String toString() {
        return this.homeTeamName + " - " + this.awayTeamName + ": " + this.homeTeamScore + " - " + this.awayTeamScore;
    }
}
