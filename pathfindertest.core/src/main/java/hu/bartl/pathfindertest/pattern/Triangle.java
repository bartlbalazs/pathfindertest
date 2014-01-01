package hu.bartl.pathfindertest.pattern;

import hu.bartl.pathfindertest.model.world.Location;

import java.util.List;

import com.google.common.collect.Lists;

public class Triangle extends ObstaclePatternBase {

	@Override
	public List<Location> getRelativeLocations() {
		List<Location> result = Lists.newArrayList();
		result.add(locationFactory.create(0, 0));
		result.add(locationFactory.create(-1, -1));
		result.add(locationFactory.create(0, -1));
		result.add(locationFactory.create(1, -1));
		result.add(locationFactory.create(-2, -2));
		result.add(locationFactory.create(-1, -2));
		result.add(locationFactory.create(0, -2));
		result.add(locationFactory.create(1, -2));
		result.add(locationFactory.create(2, -1));
		return result;
	}

}
