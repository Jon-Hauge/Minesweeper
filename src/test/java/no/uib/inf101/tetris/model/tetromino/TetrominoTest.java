package no.uib.inf101.tetris.model.tetromino;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;

public class TetrominoTest {

    @Test
    void tetrominoEqualsAndHashTest() {
        Tetromino x = Tetromino.newTetromino('L');
        Tetromino y = Tetromino.newTetromino('L');
        Tetromino z = Tetromino.newTetromino('L');

        // Reflexive
        assertTrue(x.equals(x));

        // Symmetric
        assertTrue(x.equals(y) == y.equals(x));

        // Transitive
        assertTrue((x.equals(y) && y.equals(z)) == x.equals(z));

        // Null-secure
        assertFalse(x.equals(null));

        // Mutation
        y = y.shiftedBy(1, 1);
        assertFalse(x.equals(y));

        // Hash
        assertEquals(x.hashCode(), z.hashCode());
        assertNotEquals(x.hashCode(), y.hashCode());

        // Symbols
        assertNotEquals(Tetromino.newTetromino('I'), Tetromino.newTetromino('J'));
        assertEquals(Tetromino.newTetromino('Z'), Tetromino.newTetromino('Z'));
    }

    @Test
    void illegalTetrominoTest() {
        assertThrows(IllegalArgumentException.class, ()->Tetromino.newTetromino('K'));
    }

    @Test
    void tetrominoIterationOfOT() {
        Tetromino o = Tetromino.newTetromino('O');
        o = o.shiftedBy(10, 100);
        HashSet<GridCell> cells = new HashSet<>();
        for (GridCell cell : o) {
            cells.add(cell);
            System.out.println(cell);
        }

        assertEquals(4, cells.size());
        assertTrue(cells.contains(new GridCell(new CellPosition(11, 101), 'O')));
        assertTrue(cells.contains(new GridCell(new CellPosition(11, 102), 'O')));
        assertTrue(cells.contains(new GridCell(new CellPosition(12, 101), 'O')));
        assertTrue(cells.contains(new GridCell(new CellPosition(12, 102), 'O')));
    }

    @Test
    void tetrominoIterationOfT() {
        // Create a standard 'T' tetromino placed at (10, 100) to test
        Tetromino tetro = Tetromino.newTetromino('T');
        tetro = tetro.shiftedBy(10, 100);

        // Collect which objects are iterated through
        List<GridCell> objs = new ArrayList<>();
        for (GridCell gc : tetro) {
            objs.add(gc);
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell(new CellPosition(11, 100), 'T')));
        assertTrue(objs.contains(new GridCell(new CellPosition(11, 101), 'T')));
        assertTrue(objs.contains(new GridCell(new CellPosition(11, 102), 'T')));
        assertTrue(objs.contains(new GridCell(new CellPosition(12, 101), 'T')));
    }

    @Test
    void tetrominoIterationOfS() {
        // Create a standard 'T' tetromino placed at (10, 100) to test
        Tetromino tetro = Tetromino.newTetromino('S');
        tetro = tetro.shiftedToTopCenterOf(new Grid(20, 204));

        // Collect which objects are iterated through
        List<GridCell> objs = new ArrayList<>();
        for (GridCell gc : tetro) {
            objs.add(gc);
            
        }

        // Check that we got the expected GridCell objects
        assertEquals(4, objs.size());
        assertTrue(objs.contains(new GridCell(new CellPosition(0, 102), 'S')));
        assertTrue(objs.contains(new GridCell(new CellPosition(0, 103), 'S')));
        assertTrue(objs.contains(new GridCell(new CellPosition(1, 101), 'S')));
        assertTrue(objs.contains(new GridCell(new CellPosition(1, 102), 'S')));
    }

    @Test
    void rotateTTest() {
        Tetromino tetro = Tetromino.newTetromino('T');
        tetro = tetro.rotate();
        List<GridCell> cells = new ArrayList<>();
        for (GridCell gc : tetro) {
            cells.add(gc);
            System.out.println(gc);
        }

        assertEquals(4, cells.size());
        assertTrue(cells.contains(new GridCell(new CellPosition(0, 1), 'T')));
        assertTrue(cells.contains(new GridCell(new CellPosition(1, 1), 'T')));
        assertTrue(cells.contains(new GridCell(new CellPosition(2, 1), 'T')));
        assertTrue(cells.contains(new GridCell(new CellPosition(1, 2), 'T')));
    }

}
