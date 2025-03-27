package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;

public interface ViewableTetrisModel {

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
     * An object that when iterated over returns all positions
     * and corresponding values of tetromino on board
     * @return an iterable object
     */
    Iterable<GridCell> getFallingTetromino();

    /**
     * 
     * @return current state of the game
     */
    GameState getGameState();

    /**
     * The current score of the game
     *
     * @return the score as an integer
     */
    int getScore();

    /**
     * The current level of the game
     *
     * @return the level as an integer
     */
    int getLevel();

    /**
     * The number of lines cleared in the game
     *
     * @return the number of lines as an integer
     */
    int getLines();

}
