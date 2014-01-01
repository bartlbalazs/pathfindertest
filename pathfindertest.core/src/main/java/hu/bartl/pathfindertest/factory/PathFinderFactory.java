package hu.bartl.pathfindertest.factory;

import hu.bartl.pathfindertest.algorithm.PathFinder;
import hu.bartl.pathfindertest.model.unit.Unit;
import hu.bartl.pathfindertest.model.world.Grid;
import hu.bartl.pathfindertest.modules.CoreModule;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class PathFinderFactory {

	private final Grid grid;
	private final Injector injector;

	@Inject
	public PathFinderFactory(Grid grid) {
		this.grid = grid;
		this.injector = Guice.createInjector(new CoreModule());
	}

	public PathFinder getInstance(Class<? extends PathFinder> pathFClass,
			Unit unit) {
		PathFinder pathFinder = injector.getInstance(pathFClass);
		pathFinder.setGrid(grid);
		pathFinder.setUnit(unit);
		return pathFinder;
	}
}
