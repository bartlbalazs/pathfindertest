package hu.bartl.pathfindertest.model.unit;

import hu.bartl.pathfindertest.algorithm.PathFinder;
import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.CellContent;

public interface Unit
		extends
			CellContent,
			MoveNotifier,
			FailedMoveNotifier,
			FailedMoveListener {
	void setPathFinder(PathFinder pathFinder);
	void move(Cell targetCell);
	void placeTo(Cell cell);
	int getGroupID();
}
