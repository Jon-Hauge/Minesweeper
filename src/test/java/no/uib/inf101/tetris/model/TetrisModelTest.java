package no.uib.inf101.tetris.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.tetromino.PatternedTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;

public class TetrisModelTest {

    HashSet<GridCell> getCellSet(Iterable<GridCell> cells) {
        HashSet<GridCell> set = new HashSet<>();
        for (GridCell cell : cells) {
            set.add(cell);
        }
        return set;
    }
    
    @Test
    void moveTetronimoTest() {
        TetrisModel model = new TetrisModel();
        int bottom = 0;
        for (GridCell cell : model.getFallingTetromino()) {
            if (cell.pos().row() > bottom)
                bottom = cell.pos().row();
        }
        int numRowsLeft = model.getDimension().rows()-bottom;
        for (int i=0; i<numRowsLeft-1; i++) {
            assertTrue(model.moveTetromino(1, 0));
        }
        assertFalse(model.moveTetromino(1, 0));
    }

    @Test
    void rotateTetrominoTest() {
        TetrisBoard board = new TetrisBoard(10, 10);
        TetrominoFactory factory = new PatternedTetrominoFactory("T");
        TetrisModel model = new TetrisModel(board, factory);
        assertTrue(model.moveTetromino(4, 0));
        while (model.moveTetromino(0, -1));
        assertTrue(model.rotateTetromino());
        assertTrue(model.moveTetromino(0, -1));
        
        // Check objects:
        HashSet<GridCell> cells = getCellSet(model.getFallingTetromino());
        assertTrue(cells.contains(new GridCell(new CellPosition(3, 0), 'T')));
        assertTrue(cells.contains(new GridCell(new CellPosition(4, 0), 'T')));
        assertTrue(cells.contains(new GridCell(new CellPosition(5, 0), 'T')));
        assertTrue(cells.contains(new GridCell(new CellPosition(4, 1), 'T')));

        // Check bounce rotation to the left with length 3:
        assertTrue(model.rotateTetromino());
        cells = getCellSet(model.getFallingTetromino());
        assertTrue(cells.contains(new GridCell(new CellPosition(4, 0), 'T')));
        assertTrue(cells.contains(new GridCell(new CellPosition(4, 1), 'T')));
        assertTrue(cells.contains(new GridCell(new CellPosition(4, 2), 'T')));
        assertTrue(cells.contains(new GridCell(new CellPosition(3, 1), 'T')));

        // Check bounce rotation to the right with length 4:
        model = new TetrisModel(board, new PatternedTetrominoFactory("I"));
        assertTrue(model.moveTetromino(4, 0));
        assertTrue(model.rotateTetromino());
        while(model.moveTetromino(0, 1));
        cells = getCellSet(model.getFallingTetromino());
        assertTrue(cells.contains(new GridCell(new CellPosition(3, 9), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(4, 9), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(5, 9), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(6, 9), 'I')));

        assertTrue(model.rotateTetromino());
        cells = getCellSet(model.getFallingTetromino());
        assertTrue(cells.contains(new GridCell(new CellPosition(5, 6), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(5, 7), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(5, 8), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(5, 9), 'I')));

        // Check illegal rotation:
        while(model.moveTetromino(1, 0));
        assertFalse(model.rotateTetromino());
    }

    @Test
    void dropTest() {
        TetrisModel model = new TetrisModel(new TetrisBoard(5, 6), new PatternedTetrominoFactory("I"));
        
        // Start position
        HashSet<GridCell> cells = getCellSet(model.getFallingTetromino());
        assertTrue(cells.contains(new GridCell(new CellPosition(0, 1), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(0, 2), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(0, 3), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(0, 4), 'I')));

        model.dropTetromino();

        // Board is updated:
        cells = getCellSet(model.getTilesOnBoard());
        assertTrue(cells.contains(new GridCell(new CellPosition(4, 1), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(4, 2), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(4, 3), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(4, 4), 'I')));

        // New tetromino
        cells = getCellSet(model.getFallingTetromino());
        assertTrue(cells.contains(new GridCell(new CellPosition(0, 1), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(0, 2), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(0, 3), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(0, 4), 'I')));
    }

    @Test
    void gameOverTest() {
        TetrisModel model = new TetrisModel(new TetrisBoard(5, 6), new PatternedTetrominoFactory("I"));
        for (int i=0; i<5; i++) {
            assertTrue(model.getGameState()==GameState.ACTIVE_GAME);
            model.dropTetromino();
        }
        assertTrue(model.getGameState()==GameState.GAME_OVER);
    }

    @Test
    void clockTickTest() {
        TetrisModel model = new TetrisModel(new TetrisBoard(3, 6), new PatternedTetrominoFactory("IO"));
        
        model.clockTick();
        HashSet<GridCell> cells = getCellSet(model.getFallingTetromino());
        assertTrue(cells.contains(new GridCell(new CellPosition(1, 1), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(1, 2), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(1, 3), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(1, 4), 'I')));

        model.clockTick();
        cells = getCellSet(model.getFallingTetromino());
        assertTrue(cells.contains(new GridCell(new CellPosition(2, 1), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(2, 2), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(2, 3), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(2, 4), 'I')));

        model.clockTick();
        cells = getCellSet(model.getTilesOnBoard());
        assertTrue(cells.contains(new GridCell(new CellPosition(2, 1), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(2, 2), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(2, 3), 'I')));
        assertTrue(cells.contains(new GridCell(new CellPosition(2, 4), 'I')));
        cells = getCellSet(model.getFallingTetromino());
        assertTrue(cells.contains(new GridCell(new CellPosition(0, 2), 'O')));
        assertTrue(cells.contains(new GridCell(new CellPosition(0, 3), 'O')));
        assertTrue(cells.contains(new GridCell(new CellPosition(1, 2), 'O')));
        assertTrue(cells.contains(new GridCell(new CellPosition(1, 3), 'O')));

        model.clockTick();
        cells = getCellSet(model.getTilesOnBoard());
        assertTrue(cells.contains(new GridCell(new CellPosition(0, 2), 'O')));
        assertTrue(cells.contains(new GridCell(new CellPosition(0, 3), 'O')));
        assertTrue(cells.contains(new GridCell(new CellPosition(1, 2), 'O')));
        assertTrue(cells.contains(new GridCell(new CellPosition(1, 3), 'O')));

        assertTrue(model.getGameState()==GameState.GAME_OVER);
    }

    @Test
    void scoresTest() {
        TetrisModel model = new TetrisModel(new TetrisBoard(10, 4), new PatternedTetrominoFactory("I"));
        assertEquals(0, model.getLevel());
        assertEquals(0, model.getScore());
        assertEquals(0, model.getLines());
        assertEquals(1000, model.getTimeInterval());
        model.dropTetromino();
        assertEquals(0, model.getLevel());
        assertEquals(40, model.getScore());
        assertEquals(1, model.getLines());

        for (int i=0; i<4; i++) {
            model.moveTetromino(3, 0);
            model.rotateTetromino();
            while(model.moveTetromino(0, 1));
            while(model.moveTetromino(1, 0)); 
            while(model.moveTetromino(0, -1));
            model.dropTetromino(); 
        }
        assertEquals(0, model.getLevel());
        assertEquals(1240, model.getScore());
        assertEquals(5, model.getLines());

        for (int i=0; i<6; i++) {
            model.dropTetromino();
        }

        assertEquals(1, model.getLevel());
        assertEquals(900, model.getTimeInterval());
    }
}
