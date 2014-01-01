package hu.bartl.pathfindertest.obstaclegenerator;

import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.Location;
import hu.bartl.pathfindertest.model.world.Obstacle;
import hu.bartl.pathfindertest.pattern.ObstaclePattern;

import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class FuzzyObstacleGenerator extends RandomObstacleGenerator {

	private static final String NAME = "Fuzzy";

	@Inject
	@Named("Fuzzy")
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
		for (Location location : pattern.getRelativeLocations()) {
			Cell possibleCell = getCellFromCenter(center, location);
			if (possibleCell != null && possibleCell.isEmpty()) {
				Obstacle obstacle = Obstacle.create(possibleCell);
				possibleCell.setContent(obstacle);
			}
		}
	}
}
