package hu.bartl.pathfindertest.algorithm;

import hu.bartl.pathfindertest.model.world.Cell;

import java.util.List;
import java.util.Random;

public class DummyPathFinder extends PathFinderBase {

	private final Random randomGenerator;

	public DummyPathFinder() {
		randomGenerator = new Random();
	}

	@Override
	public Cell getNextPosition(Cell targetCell) {
		Cell actualPosition = unit.getCell();
		List<Cell> emptyNeighbours = actualPosition.getEmptyNeighbours();
		int emptyNeighboursCount = emptyNeighbours.size();
		if (emptyNeighboursCount > 0) {
			int randIndex = randomGenerator.nextInt(emptyNeighboursCount);
			return emptyNeighbours.get(randIndex);
		} else {
			return actualPosition;
		}
	}

}
