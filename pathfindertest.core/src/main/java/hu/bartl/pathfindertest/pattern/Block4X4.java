package hu.bartl.pathfindertest.pattern;

import hu.bartl.pathfindertest.model.world.Location;

import java.util.List;

import com.google.inject.Inject;

public class Block4X4 extends ObstaclePatternBase {
	@Inject
	private BlockLocationFactory blockLocationFactory;

	@Override
	public List<Location> getRelativeLocations() {
		return blockLocationFactory.getRelativeLocations(4, 4);
	}
}
