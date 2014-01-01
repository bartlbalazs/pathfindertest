package hu.bartl.pathfindertest.controller;

import hu.bartl.pathfindertest.algorithm.PathFinder;
import hu.bartl.pathfindertest.factory.PathFinderFactory;
import hu.bartl.pathfindertest.model.unit.Unit;
import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.CellContent;
import hu.bartl.pathfindertest.model.world.Grid;
import hu.bartl.pathfindertest.model.world.Obstacle;
import hu.bartl.pathfindertest.model.world.Target;
import hu.bartl.pathfindertest.model.world.TargetFactory;
import hu.bartl.pathfindertest.obstaclegenerator.ObstacleGenerator;
import hu.bartl.pathfindertest.presenter.GridPresenter;
import hu.bartl.pathfindertest.unit.UnitFactory;
import hu.bartl.pathfindertest.util.UnitDistanceValueComparator;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GridControllerImpl implements GridController {

	@Inject
	private Grid grid;
	@Inject
	private SimulationRunner simulationRunner;
	@Inject
	private UnitFactory unitFactory;
	@Inject
	private TargetFactory targetFactory;
	@Inject
	private PathFinderFactory pathFinderFactory;
	@Inject
	private ObstacleGeneratorProvider obstacleGeneratorProvider;

	private List<CellContent> gridState = Lists.newArrayList();

	@Inject
	private void initializePresenter(GridPresenter gridPresenter) {
		gridPresenter.initializeLayout(grid.getRowCount(),
				grid.getColumnCount());

		for (Cell cell : grid.getCells()) {
			gridPresenter.createCellPresenterFor(cell);
		}
	}

	@Override
	public void onPrimaryInteraction(Cell cell, int modifier) {
		addUnit(cell, modifier);
	}

	private void addUnit(Cell cell, int modifier) {
		if (cell.isEmpty()) {
			Unit unit = unitFactory.create(cell, modifier % 10);
			unit.adddMoveListener(simulationRunner);
			unit.addFailedMoveListener(simulationRunner);
			cell.setContent(unit);
		}
	}

	@Override
	public void onModifiedPrimaryInteraction(Cell cell) {
		clearContent(cell);
	}

	private void clearContent(Cell cell) {
		cell.clearContent();
	}

	@Override
	public void onSecondaryInteraction(Cell cell) {
		addBlock(cell);
	}

	private void addBlock(Cell cell) {
		if (cell.isEmpty()) {
			Obstacle obstacle = Obstacle.create(cell);
			cell.setContent(obstacle);
		}
	}

	@Override
	public void onModifiedSecondaryInteraction(Cell cell, int modifier) {
		simulationRunner.setTarget(modifier % 10, cell);
	}

	public void showTargets(Map<Integer, Cell> targets) {
		if (targets == null) {
			return;
		}

		for (Cell cell : grid.getCells()) {
			if (cell.isEmpty()) {
				cell.clearContent();
			}
		}

		for (Entry<Integer, Cell> targetEntry : targets.entrySet()) {
			int targetGroup = targetEntry.getKey();
			Cell targetCell = targetEntry.getValue();
			targetCell
					.setContent(targetFactory.create(targetCell, targetGroup));
		}
	}
	@Override
	public void setPathFinderToUnits(Class<? extends PathFinder> pathFClass) {
		for (Unit unit : grid.getUnits()) {
			unit.setPathFinder(pathFinderFactory.getInstance(pathFClass, unit));
		}
	}
	@Override
	public void moveUnits(Map<Integer, Cell> targets, boolean stepWithClosest) {
		for (Unit unit : getUnits(targets, stepWithClosest)) {
			unit.move(getTargetForUnit(targets, unit));
		}
	}
	private List<Unit> getUnits(Map<Integer, Cell> targets,
			boolean orderedByClosest) {

		if (orderedByClosest) {

			Map<Unit, Double> unitDistanceMap = getUnitDistances(targets,
					grid.getUnits());
			UnitDistanceValueComparator udc = new UnitDistanceValueComparator(
					unitDistanceMap);
			TreeMap<Unit, Double> sortedUnits = new TreeMap<>(udc);
			sortedUnits.putAll(unitDistanceMap);
			return Lists.newArrayList(sortedUnits.keySet());
		} else {
			return grid.getUnits();
		}
	}

	private Map<Unit, Double> getUnitDistances(Map<Integer, Cell> targets,
			List<Unit> units) {

		Map<Unit, Double> unitDistances = Maps.newHashMap();
		for (Unit unit : units) {
			unitDistances.put(unit, calculateUnitDistance(targets, unit));
		}
		return unitDistances;
	}

	private double calculateUnitDistance(Map<Integer, Cell> targets, Unit unit) {
		return unit.getCell().getLocation()
				.getDistanceFrom(getTargetForUnit(targets, unit).getLocation());
	}

	private Cell getTargetForUnit(Map<Integer, Cell> targets, Unit unit) {
		return targets.get(unit.getGroupID());
	}

	@Override
	public void clearGrid() {
		for (Cell cell : grid.getCells()) {
			cell.clearContent();
		}
		gridState.clear();
	}

	@Override
	public void saveGridState() {
		gridState.clear();
		for (Cell cell : grid.getCells()) {
			gridState.add(cell.getContent());
		}
	}

	@Override
	public void loadGridState() {
		List<Cell> cells = grid.getCells();
		for (int i = 0; i < gridState.size(); i++) {
			CellContent content = gridState.get(i);
			Cell cell = cells.get(i);
			cell.setContent(content);
			if (content instanceof Unit) {
				((Unit) content).placeTo(cell);
			}

			if (content instanceof Target) {
				simulationRunner.setTarget(((Target) content).getGroupID(),
						cell);
			}
		}
	}

	@Override
	public boolean isGridStateSaved() {
		return !gridState.isEmpty();
	}

	@Override
	public void fillWithRandomObstacles(String generatorName, int density) {
		ObstacleGenerator generator = obstacleGeneratorProvider
				.getGeneratorTypeByName(generatorName);
		generator.fill(density);
	}

	@Override
	public int getUnitCount() {
		return grid.getUnits().size();
	}

	@Override
	public void resetDirectionVectors() {
		for (Cell cell : grid.getCells()) {
			cell.reset();
		}
	}
}
