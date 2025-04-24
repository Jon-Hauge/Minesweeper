package no.uib.inf101.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Testing the class Grid
 */
public class GridTest {

	@Test
	void gridTestGetRowsAndCols() {
		IGrid grid = new Grid(3, 2);
		assertEquals(3, grid.rows());
		assertEquals(2, grid.cols());
	}

	@Test
	void gridSanityTest() {
		IGrid grid = new Grid(3, 2);

		assertEquals(3, grid.rows());
		assertEquals(2, grid.cols());

		assertEquals(new GridCell(new CellPosition(0, 0)), grid.getCell(new CellPosition(0, 0)));
		assertEquals(new GridCell(new CellPosition(2, 1)), grid.getCell(new CellPosition(2, 1)));
	}

	@Test
	void coordinateIsOnGridTest() {
		IGrid grid = new Grid(3, 2);

		assertTrue(grid.positionIsOnGrid(new CellPosition(2, 1)));
		assertFalse(grid.positionIsOnGrid(new CellPosition(3, 1)));
		assertFalse(grid.positionIsOnGrid(new CellPosition(2, 2)));

		assertTrue(grid.positionIsOnGrid(new CellPosition(0, 0)));
		assertFalse(grid.positionIsOnGrid(new CellPosition(-1, 0)));
		assertFalse(grid.positionIsOnGrid(new CellPosition(0, -1)));
	}

	@Test
	void throwsExceptionWhenGetCoordinateOffGrid() {
		IGrid grid = new Grid(3, 2);

		try {
			@SuppressWarnings("unused")
			GridCell cell1 = grid.getCell(new CellPosition(3, 1));
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Test passed
		}
		try {
			@SuppressWarnings("unused")
			GridCell cell2 = grid.getCell(new CellPosition(3, 2));
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Test passed
		}
	}

	@Test
	void testIterator() {
		IGrid grid = new Grid(3, 2);

		List<GridCell> items = new ArrayList<>();
		for (GridCell coordinateItem : grid) {
			items.add(coordinateItem);
		}

		assertEquals(3 * 2, items.size());
		assertTrue(items.contains(new GridCell(new CellPosition(0, 0))));
		assertTrue(items.contains(new GridCell(new CellPosition(1, 1))));
		assertTrue(items.contains(new GridCell(new CellPosition(2, 1))));
		assertTrue(items.contains(new GridCell(new CellPosition(0, 1))));
	}
	
}
