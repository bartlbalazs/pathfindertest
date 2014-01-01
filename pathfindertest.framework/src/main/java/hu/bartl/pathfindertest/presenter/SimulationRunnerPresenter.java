package hu.bartl.pathfindertest.presenter;


public interface SimulationRunnerPresenter {
	void setIterationCount(int iterationCount);
	void setFailedMoveCount(double failedMoveCount);
	void setAveragePathLength(double pathLength);
	void setUnitCount(int unitCount);
}
