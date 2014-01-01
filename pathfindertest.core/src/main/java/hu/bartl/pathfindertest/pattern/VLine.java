package hu.bartl.pathfindertest.pattern;

import hu.bartl.pathfindertest.model.world.Location;

import java.util.List;

import com.google.common.collect.Lists;

public class VLine extends ObstaclePatternBase {
	@Override
	public List<Location> getRelativeLocations() {
		List<Location> result = Lists.newArrayList();
		result.add(locationFactory.create(0, 0));
		result.add(locationFactory.create(0, 1));
		result.add(locationFactory.create(0, 2));
		result.add(locationFactory.create(0, 3));
		result.add(locationFactory.create(0, 4));
		return result;
	}
}
