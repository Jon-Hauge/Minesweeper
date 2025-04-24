package no.uib.inf101.grid;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing the class GridCell
 */
public class GridCellTest {

	@Test
	void sanityTest() {
		CellPosition pos = new CellPosition(4, 2);
		GridCell gridCell = new GridCell(pos);

		assertEquals(pos, gridCell.pos());
	}

	@Test
	void gridCellEqualityAndHashCodeTest() {
		CellPosition pos1 = new CellPosition(4, 2);
		GridCell gridCell1 = new GridCell(pos1);

		CellPosition pos2 = new CellPosition(4, 2);
		GridCell gridCell2 = new GridCell(pos2);

		assertTrue(gridCell1.equals(gridCell1));
		assertTrue(gridCell2.equals(gridCell1));
		assertTrue(gridCell1.equals(gridCell2));
		assertTrue(gridCell1.hashCode() == gridCell2.hashCode());
	}

	@Test
	void gridCellInequalityTest() {
		CellPosition pos1 = new CellPosition(4, 2);
		GridCell gridCell1 = new GridCell(pos1);

		CellPosition pos2 = new CellPosition(2, 4);

		GridCell gridCell2 = new GridCell(pos2);
		GridCell gridCell3 = new GridCell(pos1);

		assertFalse(gridCell2.equals(gridCell1));
		assertFalse(gridCell1.equals(gridCell2));
		assertFalse(gridCell2.equals(gridCell3));
		assertFalse(gridCell1.equals("Not a gridcell"));
		assertFalse(gridCell1.hashCode() == gridCell2.hashCode());
		assertFalse(Objects.equals(gridCell1, gridCell2));
		assertFalse(Objects.equals(gridCell2, gridCell3));
	}

	@Test
	void cellConditionsTest() {
		CellPosition pos1 = new CellPosition(0, 0);
		CellPosition pos2 = new CellPosition(1, 1);
		CellPosition pos3 = new CellPosition(2, 2);
		CellPosition pos4 = new CellPosition(3, 3);
		
		GridCell mineCell = new GridCell(pos1);
		GridCell flaggedCell = new GridCell(pos2);
		GridCell revealedCell = new GridCell(pos3);
		GridCell neighboringCell = new GridCell(pos4);
		
		assertFalse(mineCell.hasMine());
		assertFalse(flaggedCell.isFlagged());
		assertFalse(revealedCell.isRevealed());
		assertNotEquals('3', neighboringCell.getMineCount());

		mineCell.setMine();
		flaggedCell.setFlag();
		revealedCell.revealCell();
		neighboringCell.setMineCount(3);

		assertTrue(mineCell.hasMine());
		assertTrue(flaggedCell.isFlagged());
		assertTrue(revealedCell.isRevealed());
		assertEquals(3, neighboringCell.getMineCount());
	}
	
}
