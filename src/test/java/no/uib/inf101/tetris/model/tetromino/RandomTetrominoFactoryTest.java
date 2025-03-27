package no.uib.inf101.tetris.model.tetromino;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomTetrominoFactoryTest {
    
    @Test
    void nextTetrominoNotNullTest() {
        RandomTetrominoFactory factory = new RandomTetrominoFactory();
        assertTrue(factory.getNext() instanceof Tetromino);
    }
}
