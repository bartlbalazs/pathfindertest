package hu.bartl.pathfindertest.pattern;

import hu.bartl.pathfindertest.factory.LocationFactory;
import hu.bartl.pathfindertest.model.world.Location;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

public abstract class ObstaclePatternBase implements ObstaclePattern {

	@Inject
	protected LocationFactory locationFactory;

	@Override
	public int getMinXCoordinate() {
		return Collections.min(getXCoordinates());
	}

	@Override
	public int getMaxXCoordinate() {
		return Collections.max(getXCoordinates());
	}

	private Set<Integer> getXCoordinates() {
		Set<Integer> xCoordinates = Sets.newHashSet();
		List<Location> relativeLocations = getRelativeLocations();
		for (Location location : relativeLocations) {
			xCoordinates.add(location.getXposition());
		}
		return xCoordinates;
	}

	@Override
	public int getMinYCoordinate() {
		return Collections.min(getYCoordinates());
	}

	@Override
	public int getMaxYCoordinate() {
		return Collections.max(getYCoordinates());
	}

	private Set<Integer> getYCoordinates() {
		Set<Integer> xCoordinates = Sets.newHashSet();
		List<Location> relativeLocations = getRelativeLocations();
		for (Location location : relativeLocations) {
			xCoordinates.add(location.getYposition());
		}
		return xCoordinates;
	}

}
