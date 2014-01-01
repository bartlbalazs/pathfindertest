package hu.bartl.pathfindertest.pattern;

import hu.bartl.pathfindertest.model.world.Location;

import java.util.List;

import com.google.common.collect.Lists;

public class Cross extends ObstaclePatternBase {
	@Override
	public List<Location> getRelativeLocations() {
		List<Location> result = Lists.newArrayList();
		result.add(locationFactory.create(-1, -1));
		result.add(locationFactory.create(-1, 0));
		result.add(locationFactory.create(0, 0));
		result.add(locationFactory.create(1, 0));
		result.add(locationFactory.create(1, 1));
		return result;
	}
}
