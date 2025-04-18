package no.uib.inf101.minesweeper.model;

import java.util.Random;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;

public class MinesweeperBoard extends Grid {

    private int mineCount;

    public MinesweeperBoard(int rows, int cols, int mineCount) {
        super(rows, cols, '-');
        this.mineCount = mineCount;

        placeMines();
        calculateNeighboringMines();
    }

    /** Places mines randomly on the board */
    private void placeMines() {
        int count = 0;
        Random r = new Random();
        
        while (count < mineCount) {
            int randRow = r.nextInt(rows());
            int randCol = r.nextInt(cols());
            CellPosition randPos = new CellPosition(randRow, randCol);

            if (!getCell(randPos).hasMine()) {
                getCell(randPos).setMine();
                count++;
            }
        }
    }

    /** Sets neighboring minecount of the cells on the board */
    private void calculateNeighboringMines() {
        for (GridCell cell : this) {
            if (!cell.hasMine()) {
                int adjacentMines = countMinesAround(cell.pos());
                cell.setMineCount(adjacentMines);
            }
        }
    }

    /** Counts neighboring minecount of the cells on the board
    * 
    * @param pos position to count around
    * @return number of neighboring mines
    */
    private int countMinesAround(CellPosition pos) {
        int adjMineCount = 0;
        int[][] adjacentPositions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] adjPos : adjacentPositions) {
            int newRow = pos.row() + adjPos[0];
            int newCol = pos.col() + adjPos[1];
            CellPosition newPos = new CellPosition(newRow, newCol);

            if (positionIsOnGrid(newPos) && getCell(newPos).hasMine()) {
                adjMineCount++;
            }
        }
        return adjMineCount;
    }

    /**
     * Runs the calculation for the neighboring mines
     * of the non-mine cells on the board.
     * For testing purposes.
     */
    public void testCalculation() {
        calculateNeighboringMines();
    }

}
