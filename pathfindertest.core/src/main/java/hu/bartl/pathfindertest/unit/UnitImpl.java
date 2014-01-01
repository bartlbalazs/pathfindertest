package hu.bartl.pathfindertest.unit;

import hu.bartl.pathfindertest.algorithm.PathFinder;
import hu.bartl.pathfindertest.model.unit.FailedMoveListener;
import hu.bartl.pathfindertest.model.unit.MoveListener;
import hu.bartl.pathfindertest.model.unit.Unit;
import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.DirectionVector;
import hu.bartl.pathfindertest.util.ColorModifier;

import java.awt.Color;
import java.util.List;

import com.google.common.collect.Lists;

public class UnitImpl implements Unit {

	private final int groupID;
	private Cell actualPosition;
	private PathFinder pathFinder;
	private final List<FailedMoveListener> failedMoveListeners = Lists
			.newLinkedList();
	private final List<MoveListener> moveListeners = Lists.newLinkedList();
	private final ColorModifier colorModifier;
	private static final Color BASE_COLOR = Color.GREEN;

	private UnitImpl(Cell cell, int groupID, ColorModifier colorModifier) {
		this.actualPosition = cell;
		this.groupID = groupID;
		this.colorModifier = colorModifier;
	}

	public static UnitImpl create(Cell cell, int groupID,
			ColorModifier colorModifier) {
		return new UnitImpl(cell, groupID, colorModifier);
	}

	@Override
	public Cell getCell() {
		return this.actualPosition;
	}

	@Override
	public Color getColor() {
		return colorModifier.modifyColor(BASE_COLOR, groupID);
	}

	@Override
	public boolean isBlocking() {
		return true;
	}

	@Override
	public void placeTo(Cell cell) {
		actualPosition = cell;
	}

	@Override
	public void setPathFinder(PathFinder pathFinder) {
		this.pathFinder = pathFinder;
		pathFinder.addFailedMoveListener(this);
	}

	@Override
	public void move(Cell targetCell) {
		if (pathFinder != null && targetCell != null) {
			stepTo(pathFinder.getNextPosition(targetCell));
		}
	}
	private void stepTo(Cell nextPosition) {
		if (isValidStep(nextPosition)) {
			learnMovement(actualPosition, nextPosition);
			nextPosition.setContent(this);
			actualPosition.clearContent();
			fireMoveEvent(actualPosition, nextPosition);
			actualPosition = nextPosition;
		}
	}

	private boolean isValidStep(Cell nextPosition) {
		return nextPosition != null && nextPosition.isEmpty();
	}

	private void learnMovement(Cell from, Cell to) {
		DirectionVector movement = from.getDirectionVectorTo(to);
		from.learn(movement);
		to.learn(movement);
	}

	private void fireMoveEvent(Cell actualPosition, Cell nextPosition) {
		if (!actualPosition.getLocation().equals(nextPosition.getLocation())) {
			for (MoveListener listener : moveListeners) {
				listener.onMove();
			}
		}
	}

	@Override
	public int getGroupID() {
		return this.groupID;
	}

	@Override
	public void addFailedMoveListener(FailedMoveListener listener) {
		failedMoveListeners.add(listener);
	}

	@Override
	public void removeFailedMoveListener(FailedMoveListener listener) {
		failedMoveListeners.remove(listener);
	}

	@Override
	public void onFailedMove() {
		for (FailedMoveListener listener : failedMoveListeners) {
			listener.onFailedMove();
		}
	}

	@Override
	public void adddMoveListener(MoveListener listener) {
		moveListeners.add(listener);
	}

	@Override
	public void removeMoveListener(MoveListener listener) {
		moveListeners.remove(listener);
	}
}
