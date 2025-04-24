package no.uib.inf101.minesweeper.controller;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.minesweeper.model.GameState;
import no.uib.inf101.minesweeper.model.MinesweeperBoard;

public interface ControllableMinesweeperModel {

    /**
     * Reveals cell on the chosen position, unless cell is already flagged.
     * If the cell's adjacent minecount is zero, it also reveals adjacent positions.
     * 
     * @param pos the position to reveal
     */
    void revealCell(CellPosition pos);

    /**
     * Sets flag at the chosen position.
     * Revealed cells cannot be flagged.
     * 
     * @param pos the position to flag
     */
    void setFlag(CellPosition pos);

    /** Starts the game from welcome screen */
    void startGame();

    /** Restarts the game from win or game-over screen */
    void restartGame();

    /**
     * Toggles difficulty of the game
     * between easy, medium and hard.
     */
    void toggleGameMode();

    /**
     * Sets elapsed time from the start of the game
     * 
     * @param seconds time elapsed in seconds
     */
    void setElapsedTime(int seconds);

    /** @return current state of the game */
    GameState getGameState();

    /** @return current gameboard */
    MinesweeperBoard getBoard();

}
