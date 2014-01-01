package hu.bartl.pathfindertest.obstaclegenerator;

import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.Grid;
import hu.bartl.pathfindertest.model.world.Location;
import hu.bartl.pathfindertest.pattern.ObstaclePattern;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.inject.Inject;

public abstract class RandomObstacleGenerator implements ObstacleGenerator {

	@Inject
	protected Grid grid;

	private Set<ObstaclePattern> patterns;

	private Random random = new Random();

	protected abstract Set<ObstaclePattern> getObstaclePatterns();

	@Override
	public void fill(int density) {
		if (density < 0) {
			throw new IllegalArgumentException("Density must be more than 0!");
		}

		for (int i = 0; i < density; i++) {
			generateObstacles();
		}
	}

	protected void generateObstacles() {
		patterns = getObstaclePatterns();
		int density = random.nextInt(3);
		for (int i = 0; i < density + 7; i++) {
			addPattern(getRandomCell(), getRandomPattern());
		}
	}

	protected abstract void addPattern(Cell center, ObstaclePattern pattern);

	protected Cell getCellFromCenter(Cell center, Location location) {
		Location centerLocation = center.getLocation();
		return grid.getCell(
				centerLocation.getXposition() + location.getXposition(),
				centerLocation.getYposition() + location.getYposition());
	}

	protected Cell getRandomCell() {
		List<Cell> cells = grid.getCells();
		return cells.get(random.nextInt(cells.size() - 1));
	}

	protected ObstaclePattern getRandomPattern() {
		int size = patterns.size();
		int item = random.nextInt(size);
		int i = 0;
		for (ObstaclePattern pattern : patterns) {
			if (i == item)
				return pattern;
			i += 1;
		}
		return null;
	}

}
