package hu.bartl.pathfindertest.model.world;

import hu.bartl.pathfindertest.model.unit.Unit;

import java.util.List;

public interface Grid {
	Cell getCell(Location location);
	Cell getCell(int x, int y);
	List<Cell> getNeighbours(Cell cell);
	List<Cell> getCells();
	List<Unit> getUnits();
	Location getLocation(Cell cell);
	int getRowCount();
	int getColumnCount();
}
