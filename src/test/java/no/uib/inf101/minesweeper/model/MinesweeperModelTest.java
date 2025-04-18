package no.uib.inf101.minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

/**
 * Testing the class MinesweeperModel
 */
public class MinesweeperModelTest {

    private MinesweeperModel model;
    private CellPosition testPos1;
    private CellPosition testPos2;
    private CellPosition testPos3;

    @BeforeEach
    public void initializer() {
        this.model = new MinesweeperModel();
        this.testPos1 = new CellPosition(0, 0);
        this.testPos2 = new CellPosition(5, 5);
        this.testPos3 = new CellPosition(10, 10);
    }

    @Test
    public void revealCellsTest() {
        model.revealCell(testPos1);
        assertTrue(model.getBoard().getCell(testPos1).isRevealed());

        int[][] adjacentPositions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
            };

        for (GridCell cell : model.getBoard()) {
            if (cell.getMineCount() == 0 && !cell.hasMine()) {
                model.revealCell(cell.pos());
                for (int[] adjPos : adjacentPositions) {
                    int newRow = cell.pos().row() + adjPos[0];
                    int newCol = cell.pos().col() + adjPos[1];
                    CellPosition neighborPos = new CellPosition(newRow, newCol);
                    if (model.getBoard().positionIsOnGrid(neighborPos)) {
                        assertTrue(model.getBoard().getCell(neighborPos).isRevealed());
                    }
                }
            }
        }
    }

    @Test
    public void revealMinesPostGameOverTest() {
        model.startGame();

        if (model.getGameState().equals(GameState.ACTIVE_GAME)) {
            for (GridCell cell : model.getBoard()) {
                if (cell.hasMine()) {
                    model.revealCell(cell.pos());
                }
            }
        }
        for (GridCell cell : model.getBoard()) {
            if (cell.hasMine()) {
                assertTrue(cell.isRevealed());
            }
        }
        
    }

    @Test
    public void setFlagTest() {
        model.setFlag(testPos1);
        assertTrue(model.getBoard().getCell(testPos1).isFlagged());

        //EASY
        model.toggleGameMode();
        model.toggleGameMode();
        assertEquals(5, model.getFlagCount());

        //MEDIUM
        model.toggleGameMode();
        assertEquals(10, model.getFlagCount());
        
        //HARD
        model.toggleGameMode();
        assertEquals(15, model.getFlagCount());
    }

    @Test
    public void flagLimitTest() {
        int flagCountBefore = model.getFlagCount();

        for (int i = 1; i < 11; i++) {
            model.setFlag(new CellPosition(0, i)); 
            assertEquals(flagCountBefore - i, model.getFlagCount());
        }
        model.setFlag(testPos3);
        assertFalse(model.getBoard().getCell(testPos3).isFlagged());
    }

    @Test
    public void overlapTest() {
        model.setFlag(testPos1);
        model.revealCell(testPos1);
        assertFalse(model.getBoard().getCell(testPos1).isRevealed());

        model.revealCell(testPos2);
        model.setFlag(testPos2);
        assertFalse(model.getBoard().getCell(testPos2).isFlagged());
    }

    @Test
    public void GameStateTest() {
        assertEquals(GameState.WELCOME_SCREEN, model.getGameState());

        model.startGame();
        assertEquals(GameState.ACTIVE_GAME, model.getGameState());

        for (GridCell cell : model.getBoard()) {
            if (!cell.hasMine()) {
                model.revealCell(cell.pos());
            }
        }
        assertEquals(GameState.WIN, model.getGameState());

        for (GridCell cell : model.getBoard()) {
            if (cell.hasMine()) {
                model.revealCell(cell.pos());
            }
        }
        assertEquals(GameState.GAME_OVER, model.getGameState());

        model.restartGame();
        assertEquals(GameState.WELCOME_SCREEN, model.getGameState());
    }
    
    @Test
    public void GameModeTest() {
        //Toggle-test
        if (model.getGameMode().equals(GameMode.HARD)) {
            model.toggleGameMode();
            assertEquals(GameMode.EASY, model.getGameMode());
        }
        if (model.getGameMode().equals(GameMode.EASY)) {
            model.toggleGameMode();
            assertEquals(GameMode.MEDIUM, model.getGameMode());
        }
        if (model.getGameMode().equals(GameMode.MEDIUM)) {
            model.toggleGameMode();
            assertEquals(GameMode.HARD, model.getGameMode());
        }

        // Grid- and minecount-test
        int easyMineCount = 0;
        int mediumMineCount = 0;
        int hardMineCount = 0;

        model.toggleGameMode();
        for (GridCell cell : model.getBoard()) {
            if (cell.hasMine()) {
                easyMineCount++;
            }
        }
        assertEquals(10, model.getDimension().cols());
        assertEquals(10, model.getDimension().rows());
        assertEquals(10, easyMineCount);

        model.toggleGameMode();
        if (model.getGameMode().equals(GameMode.MEDIUM)) {
            for (GridCell cell : model.getBoard()) {
                if (cell.hasMine()) {
                    mediumMineCount++;
                }
            }
            assertEquals(12, model.getDimension().cols());
            assertEquals(12, model.getDimension().rows());
            assertEquals(20, mediumMineCount);
        }

        model.toggleGameMode();
        for (GridCell cell : model.getBoard()) {
            if (cell.hasMine()) {
                hardMineCount++;
            }
        }
        assertEquals(16, model.getDimension().cols());
        assertEquals(16, model.getDimension().rows());
        assertEquals(30, hardMineCount);
    }

    @Test
    public void elapsedTimeTest() {
        model.setElapsedTime(1);
        assertEquals(1, model.getElapsedTime());
        model.setElapsedTime(100);
        assertEquals(100, model.getElapsedTime());
    }

    @Test
    void iterableTest() {
        int count = 0;
        GridDimension dimension = model.getDimension();

        for (GridCell cell : model.getTilesOnBoard()) {
            count++;
        }
        assertEquals(dimension.cols() * dimension.rows(), count);
    }

}
