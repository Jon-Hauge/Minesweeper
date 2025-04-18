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
		char item = 'x';
		CellPosition pos = new CellPosition(4, 2);
		GridCell gridCell = new GridCell(pos, item);

		assertEquals(pos, gridCell.pos());
		assertEquals(item, gridCell.symbol());
	}

	@Test
	void gridCellEqualityAndHashCodeTest() {
		char item1 = 'x';
		CellPosition pos1 = new CellPosition(4, 2);
		GridCell gridCell1 = new GridCell(pos1, item1);

		char item2 = 'x';
		CellPosition pos2 = new CellPosition(4, 2);
		GridCell gridCell2 = new GridCell(pos2, item2);

		assertTrue(gridCell1.equals(gridCell1));
		assertTrue(gridCell2.equals(gridCell1));
		assertTrue(gridCell1.equals(gridCell2));
		assertTrue(gridCell1.hashCode() == gridCell2.hashCode());
	}

	@Test
	void gridCellInequalityTest() {
		char item1 = 'x';
		CellPosition pos1 = new CellPosition(4, 2);
		GridCell gridCell1 = new GridCell(pos1, item1);

		char item2 = 'y';
		CellPosition pos2 = new CellPosition(2, 4);

		GridCell gridCell2 = new GridCell(pos2, item1);
		GridCell gridCell3 = new GridCell(pos1, item2);

		assertFalse(gridCell2.equals(gridCell1));
		assertFalse(gridCell1.equals(gridCell2));
		assertFalse(gridCell1.equals(gridCell3));
		assertFalse(gridCell2.equals(gridCell3));
		assertFalse(gridCell1.equals("Not a gridcell"));
		assertFalse(gridCell1.hashCode() == gridCell2.hashCode());
		assertFalse(Objects.equals(gridCell1, gridCell2));
		assertFalse(Objects.equals(gridCell1, gridCell3));
		assertFalse(Objects.equals(gridCell2, gridCell3));
	}

	@Test
	void cellConditionsTest() {
		CellPosition pos1 = new CellPosition(0, 0);
		CellPosition pos2 = new CellPosition(1, 1);
		CellPosition pos3 = new CellPosition(2, 2);
		CellPosition pos4 = new CellPosition(3, 3);
		
		GridCell mineCell = new GridCell(pos1, '-');
		GridCell flaggedCell = new GridCell(pos2, '-');
		GridCell revealedCell = new GridCell(pos3, '-');
		GridCell neighboringCell = new GridCell(pos4, '-');
		
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
