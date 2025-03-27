package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

public interface ControllableTetrisModel {
    
    /**
     * Moves the Tetromino
     * @param deltaRow
     * @param deltaCol
     * @return true if move was done, false otherwise
     */
    boolean moveTetromino(int deltaRow, int deltaCol);

    /**
     * Rotate the Tetromino
     * @return true if tetromino was rotated, false otherwise
     */
    boolean rotateTetromino();

    /**
     * Drops the falling tetromino and glues it to board
     */
    void dropTetromino();

    /**
     * 
     * @return current state of the game
     */
    GameState getGameState();

    
    /**
     * 
     * @return ms between each clock tick
     */
    int getTimeInterval();

    /**
     * Performs one clock tick in game
     */
    void clockTick();
}
