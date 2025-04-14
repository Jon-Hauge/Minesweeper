package no.uib.inf101.minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;

public class MinesweeperModelTest {

    private MinesweeperModel model;
    private CellPosition testPos1;
    private CellPosition testPos2;

    @BeforeEach
    public void initializer() {
        this.model = new MinesweeperModel();
        this.testPos1 = new CellPosition(0, 0);
        this.testPos2 = new CellPosition(5, 5);
    }

    @Test
    public void revealCellsTest() {
        model.getBoard().getCell(new CellPosition(1, 1)).setMine();
        model.revealCell(testPos1);
        assertTrue(model.getBoard().getCell(testPos1).isRevealed());
    }

    @Test
    public void setFlagTest() {
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

        model.setFlag(testPos1);
        assertTrue(model.getBoard().getCell(testPos1).isFlagged());
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

}
