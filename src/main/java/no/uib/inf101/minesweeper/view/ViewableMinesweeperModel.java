package no.uib.inf101.minesweeper.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.minesweeper.model.GameMode;
import no.uib.inf101.minesweeper.model.GameState;

public interface ViewableMinesweeperModel {

    /**
     * The dimensions of the board, i.e. number of rows and columns
     *
     * @return an object of type GridDimension
     */
    GridDimension getDimension();

    /**
     * An object that when iterated over returns all positions
     * and corresponding values
     *
     * @return an iterable object
     */
    Iterable<GridCell> getTilesOnBoard();

    /**
     * 
     * @return current gamestate
     */
    GameState getGameState();

    /**
     * 
     * @return current gamemode
     */
    GameMode getGameMode();

    /**
     * 
     * @return current flagcount
     */
    int getFlagCount();

    /**
     * 
     * @return current elapsed time
     */
    int getElapsedTime();

}
