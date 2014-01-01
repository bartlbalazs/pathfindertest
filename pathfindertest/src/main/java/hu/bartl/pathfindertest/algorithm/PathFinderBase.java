package hu.bartl.pathfindertest.algorithm;

import hu.bartl.pathfindertest.model.unit.FailedMoveListener;
import hu.bartl.pathfindertest.model.unit.Unit;
import hu.bartl.pathfindertest.model.world.Grid;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class PathFinderBase implements PathFinder {
	protected Unit unit;
	protected Grid grid;
	protected List<FailedMoveListener> failedMoveListeners = Lists
			.newLinkedList();

	@Override
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Override
	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	@Override
	public void addFailedMoveListener(FailedMoveListener listener) {
		failedMoveListeners.add(listener);
	}

	@Override
	public void removeFailedMoveListener(FailedMoveListener listener) {
		failedMoveListeners.remove(listener);
	}

	protected void fireFailedMoveEvent() {
		for (FailedMoveListener listener : failedMoveListeners) {
			listener.onFailedMove();
		}
	}

}
