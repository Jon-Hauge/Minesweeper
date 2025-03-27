package no.uib.inf101.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

/**
 * The Tetromino class represents a Tetris piece with a specific shape and position.
 * It implements the Iterable interface to allow iteration over its grid cells.
 */
public class Tetromino implements Iterable<GridCell> {

    private final char symbol;
    private final boolean[][] shape;
    private final CellPosition pos;

    private Tetromino(char symbol, boolean[][] shape, CellPosition pos) {
        this.symbol = symbol;
        this.shape = shape;
        this.pos = pos;
    }

    public static Tetromino newTetromino(char sym) {
        boolean[][] shape;
        switch (sym) {
            case 'I' -> shape = new boolean[][] {
                    { false, false, false, false },
                    { true, true, true, true },
                    { false, false, false, false },
                    { false, false, false, false },
            };
            case 'O' -> shape = new boolean[][] {
                    { false, false, false, false },
                    { false, true, true, false },
                    { false, true, true, false },
                    { false, false, false, false },
            };
            case 'T' -> shape = new boolean[][] {
                    { false, false, false },
                    { true, true, true },
                    { false, true, false }
            };
            case 'J' -> shape = new boolean[][] {
                    { false, false, false },
                    { true, true, true },
                    { false, false, true }
            };
            case 'L' -> shape = new boolean[][] {
                    { false, false, false },
                    { true, true, true },
                    { true, false, false }
            };
            case 'S' -> shape = new boolean[][] {
                    { false, false, false },
                    { false, true, true },
                    { true, true, false }
            };
            case 'Z' -> shape = new boolean[][] {
                    { false, false, false },
                    { true, true, false },
                    { false, true, true }
            };
            default -> throw new IllegalArgumentException(
                    "Unknown tetromino type: '" + sym + "'");
        }
        return new Tetromino(sym, shape, new CellPosition(0, 0));
    }


    /**
     * 
     * @param deltaRow
     * @param deltaCol
     * @return A new Tetromino moved by given arguments
     */
    public Tetromino shiftedBy(int deltaRow, int deltaCol) {
        return new Tetromino(symbol, shape, new CellPosition(pos.row() + deltaRow, pos.col() + deltaCol));
    }

    /**
     * 
     * @param gd dimension of grid
     * @return A new Tetromino at the top center of given dimension
     */
    public Tetromino shiftedToTopCenterOf(GridDimension gd) {
        int centerCol = gd.cols() / 2;
        return new Tetromino(symbol, shape, new CellPosition(-1, centerCol - shape.length/2));
    }

    /**
     * 
     * @return A new Tetromino rotated 90 degrees counter clockwise
     */
    public Tetromino rotate() {
        boolean[][] newShape = new boolean[shape[0].length][shape.length];
        for (int row=0; row<shape.length; row++) {
            for (int col=0; col<shape[0].length; col++) {
                int newRow = shape[0].length-1-col;
                int newCol = row;
                newShape[newRow][newCol] = shape[row][col];
            }
        }
        return new Tetromino(symbol, newShape, pos);
    }

    @Override
    public Iterator<GridCell> iterator() {
        ArrayList<GridCell> list = new ArrayList<>();
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c]) {
                    list.add(new GridCell(new CellPosition(pos.row() + r, pos.col() + c), symbol));
                }
            }
        }
        return list.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof Tetromino))
            return false;

        Tetromino tetromino = (Tetromino) other;
        return this.symbol == tetromino.symbol &&
                Arrays.deepEquals(shape, tetromino.shape) &&
                this.pos.equals(tetromino.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, Arrays.deepHashCode(shape), pos);
    }

}
