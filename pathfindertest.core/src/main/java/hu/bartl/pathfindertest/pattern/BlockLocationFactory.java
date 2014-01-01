package hu.bartl.pathfindertest.pattern;

import hu.bartl.pathfindertest.factory.LocationFactory;
import hu.bartl.pathfindertest.model.world.Location;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BlockLocationFactory {

	@Inject
	protected LocationFactory locationFactory;

	public List<Location> getRelativeLocations(int row, int column) {
		List<Location> result = Lists.newArrayList();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				result.add(locationFactory.create(j, i));
			}
		}
		return result;
	}
}
