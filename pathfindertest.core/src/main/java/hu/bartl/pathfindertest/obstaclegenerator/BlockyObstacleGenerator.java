package hu.bartl.pathfindertest.obstaclegenerator;

import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.Location;
import hu.bartl.pathfindertest.model.world.Obstacle;
import hu.bartl.pathfindertest.pattern.ObstaclePattern;

import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class BlockyObstacleGenerator extends RandomObstacleGenerator {

	private static final String NAME = "Blocky";

	@Inject
	@Named("Blocky")
	private Set<ObstaclePattern> patterns;

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected Set<ObstaclePattern> getObstaclePatterns() {
		return patterns;
	}

	protected void addPattern(Cell center, ObstaclePattern pattern) {
		if (isEnoughSpaceFor(center, pattern)) {
			for (Location location : pattern.getRelativeLocations()) {
				Cell possibleCell = getCellFromCenter(center, location);
				if (possibleCell != null && possibleCell.isEmpty()) {
					Obstacle obstacle = Obstacle.create(possibleCell);
					possibleCell.setContent(obstacle);
				}
			}
		}
	}
	private boolean isEnoughSpaceFor(Cell center, ObstaclePattern pattern) {
		if (!isTopNeighboursEmpty(center, pattern))
			return false;

		if (!isRightNeighboursEmpty(center, pattern))
			return false;

		if (!isBottomNeighboursEmpty(center, pattern))
			return false;

		if (!isLeftNeighboursEmpty(center, pattern))
			return false;

		return true;
	}

	private boolean isTopNeighboursEmpty(Cell center, ObstaclePattern pattern) {
		Location centerLocation = center.getLocation();
		int relativeMaxY = pattern.getMaxYCoordinate();
		int maxY = centerLocation.getYposition() + relativeMaxY;
		for (Location location : pattern.getRelativeLocations()) {
			Cell cell = getCellFromCenter(center, location);
			if (cell != null && cell.getLocation().getYposition() == maxY
					&& cell.getEmptyNeighbours().size() < 8)
				return false;
		}
		return true;
	}
	private boolean isRightNeighboursEmpty(Cell center, ObstaclePattern pattern) {
		Location centerLocation = center.getLocation();
		int relativeMaxX = pattern.getMaxXCoordinate();
		int maxX = centerLocation.getXposition() + relativeMaxX;
		for (Location location : pattern.getRelativeLocations()) {
			Cell cell = getCellFromCenter(center, location);
			if (cell != null && cell.getLocation().getYposition() == maxX
					&& cell.getEmptyNeighbours().size() < 8)
				return false;
		}
		return true;
	}

	private boolean isBottomNeighboursEmpty(Cell center, ObstaclePattern pattern) {
		Location centerLocation = center.getLocation();
		int relativeMinY = pattern.getMinYCoordinate();
		int minY = centerLocation.getYposition() + relativeMinY;
		for (Location location : pattern.getRelativeLocations()) {
			Cell cell = getCellFromCenter(center, location);
			if (cell != null && cell.getLocation().getYposition() == minY
					&& cell.getEmptyNeighbours().size() < 8)
				return false;
		}
		return true;
	}

	private boolean isLeftNeighboursEmpty(Cell center, ObstaclePattern pattern) {
		Location centerLocation = center.getLocation();
		int relativeMinX = pattern.getMinXCoordinate();
		int minX = centerLocation.getXposition() + relativeMinX;
		for (Location location : pattern.getRelativeLocations()) {
			Cell cell = getCellFromCenter(center, location);
			if (cell != null && cell.getLocation().getYposition() == minX
					&& cell.getEmptyNeighbours().size() < 8)
				return false;
		}
		return true;
	}
}
