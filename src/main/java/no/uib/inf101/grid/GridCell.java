package no.uib.inf101.grid;

import java.util.Objects;

/**
 * The GridCell class represents a single cellposition on the gameboard with boolean-
 * values indicating whether it has a mine, is flagged or is revealed. If the cell does
 * not have a mine, it keeps count of neighboring mines.
 */
public class GridCell {

    private CellPosition pos;
    private boolean hasMine;
    private boolean isFlagged;
    private boolean isRevealed;
    private int neighboringMines;

    public GridCell(CellPosition pos) {
        this.pos = pos;
        this.neighboringMines = 0;
        hasMine = false;
        isFlagged = false;
        isRevealed = false;
    }

    /**
     * 
     * @return the cell's position in the grid
     */
    public CellPosition pos() {
        return pos;
    }

    /**
     * Sets mine at cell
     */
    public void setMine() {
        this.hasMine = true;
    }

    /**
     * 
     * @return whether cell has a mine
     */
    public boolean hasMine() {
        return hasMine;
    }

    /**
     * Sets flag at cell
     */
    public void setFlag() {
        this.isFlagged = true;
    }

    /**
     * 
     * @return whether cell is flagged
     */
    public boolean isFlagged() {
        return isFlagged;
    }

    /**
     * Reveals cell in grid
     */
    public void revealCell() {
        this.isRevealed = true;
    }

    /**
     * 
     * @return whether cell is revealed
     */
    public boolean isRevealed() {
        return isRevealed;
    }

    /**
     * Sets number of adjacent mines of the cell
     * 
     * @param mineCount number of neighboring mines
     */
    public void setMineCount(int mineCount) {
        this.neighboringMines = mineCount;
    }

    /**
     * 
     * @return number of cell's neighboring mines
     */
    public int getMineCount() {
        return neighboringMines;
    }
    
    
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof GridCell))
            return false;

        GridCell gridCell = (GridCell) other;
        return this.pos.equals(gridCell.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos);
    }

}

