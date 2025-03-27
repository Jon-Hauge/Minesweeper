package no.uib.inf101.tetris.model.tetromino;

import java.util.Random;

public class RandomTetrominoFactory implements TetrominoFactory {

    @Override
    public Tetromino getNext() {
        Random r = new Random();
        String symbols = "LJSZTIO";
        char symbol = symbols.charAt(r.nextInt(symbols.length()));
        return Tetromino.newTetromino(symbol);
    }
    
}
