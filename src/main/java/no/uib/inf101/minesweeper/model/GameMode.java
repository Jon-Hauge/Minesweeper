package no.uib.inf101.minesweeper.model;

public enum GameMode {

    EASY,
    MEDIUM,
    HARD;

    public GameMode next() {
        return switch (this) {
            case EASY -> MEDIUM;
            case MEDIUM -> HARD;
            case HARD -> EASY;
        };
    }

}
