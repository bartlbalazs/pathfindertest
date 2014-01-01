package hu.bartl.pathfindertest.controller;

import hu.bartl.pathfindertest.algorithm.PathFinder;
import hu.bartl.pathfindertest.model.world.Cell;

import java.util.Map;

public interface GridController {
	void onPrimaryInteraction(Cell cell, int modifier);
	void onModifiedPrimaryInteraction(Cell cell);
	void onSecondaryInteraction(Cell cell);
	void onModifiedSecondaryInteraction(Cell cell, int modifier);
	void showTargets(Map<Integer, Cell> targets);
	void setPathFinderToUnits(Class<? extends PathFinder> pathFClass);
	void moveUnits(Map<Integer, Cell> targets, boolean stepWithClosest);
	void clearGrid();
	void saveGridState();
	void loadGridState();
	void resetDirectionVectors();
	boolean isGridStateSaved();
	void fillWithRandomObstacles(String generatorName, int density);
	int getUnitCount();
}
