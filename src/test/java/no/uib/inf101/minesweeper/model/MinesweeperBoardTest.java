package no.uib.inf101.minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;

/**
 * Testing the class MinesweeperBoard
 */
public class MinesweeperBoardTest {

    @Test
    public void placeMinesTest() {
        MinesweeperBoard board = new MinesweeperBoard(10, 10, 10);
        int count = 0;

        for (GridCell cell : board) {
            if (cell.hasMine()) {
                count++;
            }
        }
        assertEquals(10, count);
    }

    @Test
    public void calculateNeighboringMinesTest() {
        MinesweeperBoard board = new MinesweeperBoard(10, 10, 0);

        //Upper-left corner
        board.getCell(new CellPosition(0, 1)).setMine();
        board.getCell(new CellPosition(1, 0)).setMine();
        CellPosition neighbor1 = new CellPosition(0, 0);

        //Upper-right corner
        board.getCell(new CellPosition(0, 9)).setMine();
        board.getCell(new CellPosition(1, 9)).setMine();
        CellPosition neighbor2 = new CellPosition(0, 8);

        //Lower-left corner
        board.getCell(new CellPosition(8, 0)).setMine();
        board.getCell(new CellPosition(9, 0)).setMine();
        CellPosition neighbor3 = new CellPosition(9, 1);

        //Lower-right corner
        board.getCell(new CellPosition(8, 9)).setMine();
        board.getCell(new CellPosition(9, 9)).setMine();
        CellPosition neighbor4 = new CellPosition(8, 8);

        //Middle
        board.getCell(new CellPosition(2, 2)).setMine();
        board.getCell(new CellPosition(2, 3)).setMine();
        board.getCell(new CellPosition(2, 4)).setMine();
        board.getCell(new CellPosition(3, 2)).setMine();
        board.getCell(new CellPosition(3, 4)).setMine();
        board.getCell(new CellPosition(4, 2)).setMine();
        board.getCell(new CellPosition(4, 3)).setMine();
        board.getCell(new CellPosition(4, 4)).setMine();
        CellPosition neighbor5 = new CellPosition(3, 3);

        //No neighboring mines
        CellPosition noNeighbors = new CellPosition(7, 5);
    
        board.testCalculation();

        for (GridCell cell : board) {
            if (cell.pos().equals(neighbor1)) {
                assertEquals(2, cell.getMineCount());
            }
            if (cell.pos().equals(neighbor2)) {
                assertEquals(2, cell.getMineCount());
            }
            if (cell.pos().equals(neighbor3)) {
                assertEquals(2, cell.getMineCount());
            }
            if (cell.pos().equals(neighbor4)) {
                assertEquals(2, cell.getMineCount());
            }
            if (cell.pos().equals(neighbor5)) {
                assertEquals(8, cell.getMineCount());
            }
            if (cell.pos().equals(noNeighbors)) {
                assertEquals(0, cell.getMineCount());
            }
        }
    }

}
