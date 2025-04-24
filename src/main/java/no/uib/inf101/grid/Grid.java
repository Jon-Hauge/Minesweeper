package no.uib.inf101.grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Grid implements IGrid {

    private int rows;
    private int cols;
    private List<List<GridCell>> cells;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        cells = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            List<GridCell> innerList = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                GridCell defaultCell = new GridCell(new CellPosition(i, j));
                innerList.add(defaultCell);
            }
            cells.add(innerList);
        }
    }

    @Override
    public int rows() {
        return this.rows;
    }

    @Override
    public int cols() {
        return this.cols;
    }

    /* Ny iterator for å omgjøre Grid's interne verdier til GridCell istedenfor Character
    * ble hjulpet av OpenAI's ChatGPT. Dette gjelder kun addAll-funksjonen. Koden er forstått
    * ihht til seg selv og resten av programmet.
    * OpenAI - https://chatgpt.com - henta 03.04.25
    */
    @Override
    public Iterator<GridCell> iterator() {
        List<GridCell> gridCells = new ArrayList<>();

        for (List<GridCell> cell : cells) {
            gridCells.addAll(cell);
        }
        return gridCells.iterator();
    }

    @Override
    public GridCell getCell(CellPosition pos) {
        if (!positionIsOnGrid(pos))
            throw new IndexOutOfBoundsException("Given position is not within grid: (" + pos.row() + ", " + pos.col() + ")");

        int row = pos.row();
        int col = pos.col();
        return cells.get(row).get(col);
    }

    @Override
    public boolean positionIsOnGrid(CellPosition pos) {
        boolean isWithinRowBound = pos.row() >= 0 && pos.row() < rows;
        boolean isWithinColBound = pos.col() >= 0 && pos.col() < cols;

        return isWithinRowBound && isWithinColBound;
    }
    
}
