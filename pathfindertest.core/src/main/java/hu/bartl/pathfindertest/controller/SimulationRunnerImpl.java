package hu.bartl.pathfindertest.controller;

import hu.bartl.pathfindertest.algorithm.PathFinder;
import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.presenter.SimulationRunnerPresenter;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SimulationRunnerImpl implements SimulationRunner {

	private final GridController gridController;
	private final SimulationRunnerPresenter simulationRunnerPresenter;

	private Timer timer = new Timer();
	private int periodTime = 1000;
	private boolean isRunning = false;
	private final AlgorithmProvider algorithmProvider;
	private Class<? extends PathFinder> algorithmType;
	private Map<Integer, Cell> targets = Maps.newHashMap();
	private int iterationCount = 0;
	private int failedMoveCount = 0;
	private String obstacleGeneratorName = "";
	private boolean stepWithClosest = false;
	private int obstacleDensity = 5;
	private boolean unitsMovedInLastIteration = false;
	private int unitMoves = 0;
	private int unitCount = 0;

	@Inject
	public SimulationRunnerImpl(GridController gridController,
			SimulationRunnerPresenter simulationRunnerPresenter,
			AlgorithmProvider algorithmProvider,
			ObstacleGeneratorProvider obstacleGeneratorProvider) {
		this.gridController = gridController;
		this.simulationRunnerPresenter = simulationRunnerPresenter;
		this.algorithmProvider = algorithmProvider;
		this.algorithmType = algorithmProvider.getDefaultAlgorithmType();
		this.obstacleGeneratorName = obstacleGeneratorProvider
				.getDefaultGeneratorName();
	}

	@Override
	public void start() {
		stop();
		if (!gridController.isGridStateSaved()) {
			gridController.saveGridState();
		}
		simulationRunnerPresenter.setUnitCount(unitCount = gridController
				.getUnitCount());

		gridController.setPathFinderToUnits(algorithmType);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				triggerSimulationIteration();
			}
		}, periodTime, periodTime);
		isRunning = true;
	}

	@Override
	public void stop() {
		updateFailedMoveCount();
		isRunning = false;
		timer.cancel();
		timer.purge();
		timer = new Timer();
	}

	private void triggerSimulationIteration() {
		unitsMovedInLastIteration = false;
		gridController.moveUnits(targets, stepWithClosest);
		simulationRunnerPresenter.setIterationCount(iterationCount++);
		if (!unitsMovedInLastIteration) {
			stop();
		}
	}
	@Override
	public void reset() {
		stop();
		targets.clear();
		gridController.loadGridState();
		gridController.resetDirectionVectors();
		resetStatistics();
	}

	private void resetStatistics() {
		simulationRunnerPresenter.setUnitCount(unitCount = 0);
		simulationRunnerPresenter.setIterationCount(iterationCount = 0);
		simulationRunnerPresenter.setFailedMoveCount(failedMoveCount = 0);
		simulationRunnerPresenter.setAveragePathLength(unitMoves = 0);
	}

	@Override
	public void clear() {
		stop();
		gridController.clearGrid();
		gridController.resetDirectionVectors();
		resetStatistics();
		targets.clear();
	}

	@Override
	public void setSpeed(double movesPerSec) {
		periodTime = (int) (10000 / (movesPerSec * 10));
		if (isRunning) {
			start();
		}
	}

	@Override
	public void setTarget(int groupID, Cell target) {
		targets.put(groupID, target);
		gridController.resetDirectionVectors();
		gridController.showTargets(targets);
	}

	@Override
	public void onFailedMove() {
		failedMoveCount++;
		if (failedMoveCount != 0 && unitCount != 0
				&& failedMoveCount % unitCount == 0) {
			updateFailedMoveCount();
		}
	}
	private void updateFailedMoveCount() {
		double displayedCount = failedMoveCount * 1d / unitCount;

		if (Double.isNaN(displayedCount))
			displayedCount = 0;

		simulationRunnerPresenter.setFailedMoveCount(displayedCount);
	}

	@Override
	public void setObstacleGeneratorName(String generatorName) {
		this.obstacleGeneratorName = generatorName;
	}

	@Override
	public void setObstacleDensity(int density) {
		this.obstacleDensity = density;
	}

	@Override
	public void generateObstacles() {
		gridController.fillWithRandomObstacles(obstacleGeneratorName,
				obstacleDensity);
	}

	@Override
	public void onMove() {
		unitsMovedInLastIteration = true;
		unitMoves++;
		simulationRunnerPresenter.setAveragePathLength(unitMoves * 1d
				/ unitCount);
	}

	@Override
	public void setAlgorithmType(String algorithmName) {
		algorithmType = algorithmProvider.getAlgorithmTypeByName(algorithmName);
	}

	@Override
	public void setStepWithClosest(boolean stepWithClosest) {
		this.stepWithClosest = stepWithClosest;
	}
}
