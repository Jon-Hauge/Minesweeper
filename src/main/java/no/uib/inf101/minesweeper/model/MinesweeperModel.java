package no.uib.inf101.minesweeper.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.minesweeper.controller.ControllableMinesweeperModel;
import no.uib.inf101.minesweeper.view.ViewableMinesweeperModel;

/**
 * The MinesweeperModel class implements the core logic for a Minesweeper game.
 * It manages the board, actions, gamestate and gamemode.
 * It also provides methods to control the game and retrieve game information.
 */
public class MinesweeperModel implements ViewableMinesweeperModel, ControllableMinesweeperModel {

    private MinesweeperBoard board;
    private GameState state;
    private GameMode mode;
    private int flagCount;
    private int elapsedTime;

    public MinesweeperModel() {
        state = GameState.WELCOME_SCREEN;
        mode = GameMode.MEDIUM;
        boardInitializer(mode);
    }

    private void boardInitializer(GameMode mode) {
        if (mode.equals(GameMode.EASY)) {
            board = new MinesweeperBoard(10, 10, 10);
            flagCount = 5;
        }
        if (mode.equals(GameMode.MEDIUM)) {
            board = new MinesweeperBoard(12, 12, 20);
            flagCount = 10;
        }
        if (mode.equals(GameMode.HARD)) {
            board = new MinesweeperBoard(16, 16, 30);
            flagCount = 15;
        }
    }

    @Override
    public void revealCell(CellPosition pos) {
        int[][] adjacentPositions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
        };
        GridCell currentCell = board.getCell(pos);

        if (!currentCell.isFlagged() && !currentCell.isRevealed()) {
            currentCell.revealCell();
            
            if (currentCell.getMineCount() == 0 && !currentCell.hasMine()) {
                for (int[] adjPos : adjacentPositions) {
                    int newRow = pos.row() + adjPos[0];
                    int newCol = pos.col() + adjPos[1];
                    CellPosition newPos = new CellPosition(newRow, newCol);
                    if (board.positionIsOnGrid(newPos)) {
                        revealCell(newPos);
                    }
                }
            }

            if (board.getCell(pos).hasMine()) {
                state = GameState.GAME_OVER;
            }
        }

        if (state.equals(GameState.GAME_OVER)) {
            for (GridCell cell : board) {
                if (cell.hasMine()) {
                    cell.revealCell();
                }
            }
        }

        if (checkWinCondition()) {
            state = GameState.WIN;
        }
    }

    @Override
    public void setFlag(CellPosition pos) {
        if (!board.getCell(pos).isRevealed() && !board.getCell(pos).isFlagged()) {
            if (flagCount > 0) {
                board.getCell(pos).setFlag();
                flagCount--;
            }
        }

        if (checkWinCondition()) {
            state = GameState.WIN;
        }
    }

    private boolean checkWinCondition() {
        int totalCells = board.rows() * board.cols();
        int count = 0;

        for (GridCell cell : board) {
            if (cell.isRevealed()) {
                count++;
            }
            if (cell.hasMine() || cell.isFlagged()) {
                totalCells--;
            }
        }
        if (count == totalCells) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void startGame() {
        state = GameState.ACTIVE_GAME;
    }

    @Override
    public void restartGame() {
        boardInitializer(mode);
        state = GameState.WELCOME_SCREEN;
    }

    @Override
    public void toggleGameMode() {
        mode = mode.next();
        restartGame();
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
    public MinesweeperBoard getBoard() {
        return board;
    }

    @Override
    public GameState getGameState() {
        return state;
    }

    @Override
    public GameMode getGameMode() {
        return mode;
    }

    @Override
    public int getFlagCount() {
        return flagCount;
    }
    
    @Override
    public void setElapsedTime(int seconds) {
        this.elapsedTime = seconds;
    }

    @Override
    public int getElapsedTime() {
        return elapsedTime;
    }

}