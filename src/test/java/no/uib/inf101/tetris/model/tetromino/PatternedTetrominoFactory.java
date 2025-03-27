package no.uib.inf101.tetris.model.tetromino;

public class PatternedTetrominoFactory implements TetrominoFactory {

    String order;
    int orderLength;
    int currentIndex;
    
    public PatternedTetrominoFactory(String order) {
        this.order = order;
        this.orderLength = order.length();
        this.currentIndex = 0;
    }
    
    @Override
    public Tetromino getNext() {
        Tetromino next = Tetromino.newTetromino(order.charAt(currentIndex));
        currentIndex = (currentIndex+1)%orderLength;
        return next;
    }
    
}
