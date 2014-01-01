package hu.bartl.pathfindertest.controller;

import hu.bartl.pathfindertest.model.unit.FailedMoveListener;
import hu.bartl.pathfindertest.model.unit.MoveListener;
import hu.bartl.pathfindertest.model.world.Cell;

public interface SimulationRunner extends MoveListener, FailedMoveListener {
	void start();
	void stop();
	void reset();
	void clear();
	void setSpeed(double movesPerSec);
	void setAlgorithmType(String algorithmName);
	void setTarget(int groupID, Cell cell);
	void setObstacleGeneratorName(String generatorName);
	void setObstacleDensity(int density);
	void generateObstacles();
	void setStepWithClosest(boolean stepWithClosest);
}
