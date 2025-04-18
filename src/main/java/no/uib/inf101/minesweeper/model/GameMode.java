package no.uib.inf101.minesweeper.model;

public enum GameMode {

    EASY,
    MEDIUM,
    HARD;

    /**
     * Toggles to next gamemode
     * 
     * @return next gamemode based on current
     */
    public GameMode next() {
        return switch (this) {
            case EASY -> MEDIUM;
            case MEDIUM -> HARD;
            case HARD -> EASY;
        };
    }

}
