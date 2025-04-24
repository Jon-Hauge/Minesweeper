package no.uib.inf101.grid;

public interface IGrid extends GridDimension, Iterable<GridCell> {

	/**
	 * Gets the gridcell at the given coordinate.
	 * 
	 * @param pos the position to get
	 * @return the gridcell stored at the position
	 * @throws IndexOutOfBoundsException if the position does not exist in the grid
	 */
	GridCell getCell(CellPosition pos);

	/**
	 * Reports whether the position is within bounds for this grid
	 * 
	 * @param pos position to check
	 * @return true if the coordinate is within bounds, false otherwise
	 */
	boolean positionIsOnGrid(CellPosition pos);
}
