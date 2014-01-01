package hu.bartl.pathfindertest.algorithm;

import hu.bartl.pathfindertest.model.world.Cell;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class AStarV1 extends PathFinderBase {

	private Cell start;
	private Cell target;
	private List<Cell> openSet;
	private List<Cell> closedSet;
	private Map<Cell, Cell> cameFrom; // K: to, V: from
	private Map<Cell, Double> gScore;
	private Map<Cell, Double> fScore;
	private LinkedList<Cell> plannedPath = null;

	private LinkedList<Cell> getPathToTarget(Cell start, Cell target) {
		initPathFinding(target);

		while (!openSet.isEmpty()) {
			Cell currentCell = getNextCellFromOpenSet();
			if (currentCell.equals(target)) {
				LinkedList<Cell> reconstructPath = reconstructPath(currentCell);
				if (reconstructPath.size() > 1) {
					reconstructPath.removeFirst();
				}
				return reconstructPath;
			}
			openSet.remove(currentCell);
			closedSet.add(currentCell);
			for (Cell neighbour : currentCell.getEmptyNeighbours()) {
				double tentativeGscore = gScore.get(currentCell) + 1;
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

		return buildNoMovePath();
	}

	private LinkedList<Cell> buildNoMovePath() {
		LinkedList<Cell> result = Lists.newLinkedList();
		result.add(getActualPosition());
		return result;
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

	private LinkedList<Cell> reconstructPath(Cell currentCell) {
		if (cameFrom.keySet().contains(currentCell)) {
			LinkedList<Cell> path = reconstructPath(cameFrom.get(currentCell));
			path.add(currentCell);
			return path;
		} else {
			LinkedList<Cell> path = Lists.newLinkedList();
			path.add(currentCell);
			return path;
		}
	}

	@Override
	public Cell getNextPosition(Cell targetCell) {
		if (plannedPath != null && !plannedPath.isEmpty()) {
			Cell plannedStep = plannedPath.removeFirst();
			if (plannedStep.isEmpty()) {
				return plannedStep;
			}
		}
		if (plannedPath != null) {
			fireFailedMoveEvent();
		}
		plannedPath = getPathToTarget(getActualPosition(),
				getAdjusedtTarget(targetCell));
		return plannedPath.removeFirst();
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
