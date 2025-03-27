package no.uib.inf101.tetris.model.tetromino;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PatternedTetrominoFactoryTest {
    
    @Test
    void PatternedTetrominoFactoryOrderTest() {
        String order = "LOL";
        PatternedTetrominoFactory factory = new PatternedTetrominoFactory(order);

        assertEquals(Tetromino.newTetromino('L'), factory.getNext());
        assertEquals(Tetromino.newTetromino('O'), factory.getNext());
        assertEquals(Tetromino.newTetromino('L'), factory.getNext());
        assertEquals(Tetromino.newTetromino('L'), factory.getNext());
        assertEquals(Tetromino.newTetromino('O'), factory.getNext());
        assertEquals(Tetromino.newTetromino('L'), factory.getNext());
    }
}
