package hu.bartl.pathfindertest.algorithm;

import hu.bartl.pathfindertest.model.unit.FailedMoveNotifier;
import hu.bartl.pathfindertest.model.unit.Unit;
import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.Grid;

public interface PathFinder extends FailedMoveNotifier {
	void setGrid(Grid grid);
	void setUnit(Unit unit);
	Cell getNextPosition(Cell targetCell);
}
