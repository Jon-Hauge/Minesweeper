package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.RandomTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

/**
 * The TetrisModel class implements the core logic for a Tetris game.
 * It manages the game board, the falling tetromino, and the game state.
 * It also provides methods to control the game and retrieve game information.
 */
public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel {

    private TetrisBoard board;
    private TetrominoFactory factory;
    private Tetromino fallingTetromino;
    private GameState state;

    private int lines;
    private int score;

    public TetrisModel() {
        this(new TetrisBoard(15, 10), new RandomTetrominoFactory());
    }

    public TetrisModel(TetrisBoard board, TetrominoFactory factory) {
        this.board = board;
        this.factory = factory;
        this.fallingTetromino = factory.getNext().shiftedToTopCenterOf(board);
        state = GameState.ACTIVE_GAME;
        lines = 0;
        score = 0;
    }

    @Override
    public GridDimension getDimension() {
        return board;
    }

    @Override
    public Iterable<GridCell> getTilesOnBoard() {
        return board;
    }

    @Override
    public Iterable<GridCell> getFallingTetromino() {
        return fallingTetromino;
    }

    @Override
    public boolean moveTetromino(int deltaRow, int deltaCol) {
        Tetromino shifted = fallingTetromino.shiftedBy(deltaRow, deltaCol);
        if (isLegalPosition(shifted)) {
            this.fallingTetromino = shifted;
            return true;
        }
        return false;
    }

    private boolean isLegalPosition(Tetromino tetromino) {
        for (GridCell cell : tetromino) {
            if (!board.positionIsOnGrid(cell.pos()) || !board.get(cell.pos()).equals('-'))
                return false;
        }
        return true;
    }

    @Override
    public boolean rotateTetromino() {
        Tetromino rotated = fallingTetromino.rotate();
        if (isLegalPosition(rotated)) {
            this.fallingTetromino = rotated;
            return true;
        } else {
            // Try to bounce the tetromino back into the board
            for (int i = 1; i <= 2; i++) {
                Tetromino shiftedLeft = rotated.shiftedBy(0, -i);
                Tetromino shiftedRight = rotated.shiftedBy(0, i);
                if (isLegalPosition(shiftedLeft)) {
                    this.fallingTetromino = shiftedLeft;
                    return true;
                } else if (isLegalPosition(shiftedRight)) {
                    this.fallingTetromino = shiftedRight;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void dropTetromino() {
        while (moveTetromino(1, 0));
        glueTetromino(fallingTetromino);
    }

    private void glueTetromino(Tetromino tetromino) {
        for (GridCell cell : tetromino) {
            board.set(cell.pos(), cell.symbol());
        }
        int linesRemoved = board.removeFullRows();
        updateScores(linesRemoved);
        fallingTetromino = factory.getNext().shiftedToTopCenterOf(board);
        if (!isLegalPosition(fallingTetromino)) {
            this.state = GameState.GAME_OVER;
        }
    }

    private void updateScores(int linesRemoved) {
        this.lines += linesRemoved;
        switch (linesRemoved) {
            case (1) -> score += 40 * (this.getLevel() + 1);
            case (2) -> score += 100 * (this.getLevel() + 1);
            case (3) -> score += 300 * (this.getLevel() + 1);
            case (4) -> score += 1200 * (this.getLevel() + 1);
        }
    }

    @Override
    public GameState getGameState() {
        return state;
    }

    @Override
    public int getTimeInterval() {
        return (int) (1000 * Math.pow(0.9, getLevel()));
    }

    @Override
    public void clockTick() {
        if (!moveTetromino(1, 0)) {
            glueTetromino(fallingTetromino);
        }
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public int getLevel() {
        return lines / 10;
    }

    @Override
    public int getLines() {
        return this.lines;
    }
}
