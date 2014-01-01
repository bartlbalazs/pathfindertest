package hu.bartl.pathfindertest.pattern;

import hu.bartl.pathfindertest.model.world.Location;

import java.util.List;

public interface ObstaclePattern {
	List<Location> getRelativeLocations();
	int getMinXCoordinate();
	int getMaxXCoordinate();
	int getMinYCoordinate();
	int getMaxYCoordinate();
}
