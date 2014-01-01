package hu.bartl.pathfindertest.algorithm;

import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.DirectionVector;
import hu.bartl.pathfindertest.model.world.DirectionVector.MovementVectorFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

public class AStarWithDM extends PathFinderBase {

	@Inject
	private MovementVectorFactory movementVectorFactory;

	private Cell start;
	private Cell target;
	private List<Cell> openSet;
	private List<Cell> closedSet;
	private Map<Cell, Cell> cameFrom; // K: to, V: from
	private Map<Cell, Double> gScore;
	private Map<Cell, Double> fScore;
	private Cell plannedNextStep;

	private List<Cell> getPathToTarget(Cell start, Cell target) {
		initPathFinding(target);

		while (!openSet.isEmpty()) {
			Cell currentCell = getNextCellFromOpenSet();
			if (currentCell.equals(target)) {
				return reconstructPath(currentCell);
			}
			openSet.remove(currentCell);
			closedSet.add(currentCell);
			for (Cell neighbour : currentCell.getEmptyNeighbours()) {
				double tentativeGscore = gScore.get(currentCell)
						+ getStepCostTo(currentCell, neighbour);
				double tentativeFscore = tentativeGscore + h(neighbour);

				if (closedSet.contains(neighbour)
						&& tentativeFscore >= fScore.get(neighbour)) {
					continue;
				}

				if (!openSet.contains(neighbour)
						|| tentativeFscore < fScore.get(neighbour)) {
					cameFrom.put(neighbour, currentCell);
					gScore.put(neighbour, tentativeGscore);
					fScore.put(neighbour, tentativeFscore);
					if (!openSet.contains(neighbour)) {
						openSet.add(neighbour);
					}
				}
			}
		}

		return Lists.newArrayList();
	}
	private void initPathFinding(Cell target) {
		this.target = target;
		this.openSet = Lists.newArrayList();
		this.closedSet = Lists.newArrayList();
		this.cameFrom = Maps.newHashMap();
		this.gScore = Maps.newHashMap();
		this.fScore = Maps.newHashMap();
		addInitialValues();
	}

	private void addInitialValues() {
		start = unit.getCell();
		openSet.add(start);
		gScore.put(start, 0d);
		fScore.put(start, gScore.get(start));
	}

	private Cell getNextCellFromOpenSet() {
		return getClosestCellToTarget(openSet);
	}

	private double getStepCostTo(Cell from, Cell to) {
		if (!from.getEmptyNeighbours().contains(to)) {
			throw new IllegalArgumentException(
					"The destination must be an empty neighbour of the start point!");
		}
		DirectionVector fromDV = from.getDirectionVector();
		DirectionVector toDV = to.getDirectionVector();
		DirectionVector mv = movementVectorFactory.create(from, to);
		return 1 + 0.25 * (2 - fromDV.scalarProduct(mv) - (toDV
				.scalarProduct(mv)));
	}

	private Cell getClosestCellToTarget(List<Cell> cells) {
		Map<Double, Cell> hValues = Maps.newHashMap();
		for (Cell cell : cells) {
			hValues.put(h(cell), cell);
		}
		double minValue = Collections.min(hValues.keySet());
		return hValues.get(minValue);
	}

	private double h(Cell cell) {
		return cell.getDistanceFrom(target);
	}

	private List<Cell> reconstructPath(Cell currentCell) {
		if (cameFrom.keySet().contains(currentCell)) {
			List<Cell> path = reconstructPath(cameFrom.get(currentCell));
			path.add(currentCell);
			return path;
		} else {
			ArrayList<Cell> path = Lists.newArrayList();
			path.add(currentCell);
			return path;
		}
	}

	@Override
	public Cell getNextPosition(Cell targetCell) {
		Cell position = getActualPosition();
		Cell step;
		if (targetCell.equals(position)) {
			step = targetCell;
		}

		Cell adjustesTarget = getAdjusedtTarget(targetCell);

		List<Cell> pathToTarget = getPathToTarget(position, adjustesTarget);
		if (pathToTarget.size() > 1) {
			step = pathToTarget.get(1);
		} else {
			step = getClosestCellToTarget(position.getNeighbours());
		}
		if (pathToTarget.size() > 2) {
			checkForFailedMove(step, pathToTarget.get(2));
		}

		return step;
	}

	private void checkForFailedMove(Cell step, Cell nextStep) {
		if (plannedNextStep != null
				&& !plannedNextStep.getLocation().equals(step.getLocation())) {
			fireFailedMoveEvent();
		}
		plannedNextStep = nextStep;
	}

	private Cell getActualPosition() {
		return unit.getCell();
	}

	private Cell getAdjusedtTarget(Cell targetCell) {
		Cell position = getActualPosition();
		if (targetCell.isEmpty()) {
			return targetCell;
		}

		Cell closestCellToTarget = getClosestCellToTarget(targetCell
				.getClosestEmptyCells());
		if (h(closestCellToTarget) < h(position)) {
			return closestCellToTarget;
		} else {
			return position;
		}
	}
}
