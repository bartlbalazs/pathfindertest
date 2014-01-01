package hu.bartl.pathfindertest.factory;

import hu.bartl.pathfindertest.model.world.Location;
import hu.bartl.pathfindertest.model.world.LocationImpl;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;

@Singleton
public class LocationFactory {
	public Location create(int x, int y) {
		return LocationImpl.create(x, y);
	}

	public List<Location> createNeighbourLocations(Location location) {
		List<Location> neighbourLocations = Lists.newArrayList();
		for (int c = -1; c <= 1; c++) {
			for (int r = -1; r <= 1; r++) {
				Location neighbour = create(location.getXposition() + c,
						location.getYposition() + r);
				neighbourLocations.add(neighbour);
			}
		}
		return neighbourLocations;
	}

}
