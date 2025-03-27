package no.uib.inf101.tetris.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;

public class TetrisBoardTest {

    @Test
    public void prettyStringTest() {
        TetrisBoard board = new TetrisBoard(3, 4);

        String expected = String.join("\n", new String[] {
                "----",
                "----",
                "----"
        });
        assertEquals(expected, board.prettyString());
    }

    @Test
    public void removeFullRowsTest() {
        TetrisBoard board = createBoard(new String[] {
                "-T",
                "TT",
                "LT",
                "L-",
                "LL"
        });

        assertEquals(3, board.removeFullRows());

        String expected = String.join("\n", new String[] {
                "--",
                "--",
                "--",
                "-T",
                "L-"
        });
        assertEquals(expected, board.prettyString());
    }

    @Test
    public void removeFullRowsBottomRowKeptTest() {
        TetrisBoard board = createBoard(new String[] {
                "TT",
                "TT",
                "LL",
                "L-"
        });

        assertEquals(3, board.removeFullRows());

        String expected = String.join("\n", new String[] {
                "--",
                "--",
                "--",
                "L-"
        });
        assertEquals(expected, board.prettyString());
    }

    @Test
    public void removeFullRowsAllRowsRemovedTest() {
        TetrisBoard board = createBoard(new String[] {
                "LL",
                "LL",
                "TT",
                "TT"
        });

        assertEquals(4, board.removeFullRows());

        String expected = String.join("\n", new String[] {
                "--",
                "--",
                "--",
                "--"
        });
        assertEquals(expected, board.prettyString());
    }

    @Test
    public void removeFullRowsDifferentWidthTest() {
        TetrisBoard board = createBoard(new String[] {
                "TTT",
                "TTT",
                "LLL",
                "LLL"
        });

        assertEquals(4, board.removeFullRows());

        String expected = String.join("\n", new String[] {
                "---",
                "---",
                "---",
                "---"
        });
        assertEquals(expected, board.prettyString());
    }

    

    private TetrisBoard createBoard(String[] strings) {
        int rows = strings.length;
        int cols = strings[0].length();
        TetrisBoard board = new TetrisBoard(rows, cols);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char c = strings[row].charAt(col);
                if (c != '-') {
                    board.set(new CellPosition(row, col), c);
                }
            }
        }

        return board;
    }

}
