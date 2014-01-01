package hu.bartl.pathfindertest.obstaclegenerator;

import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.Grid;
import hu.bartl.pathfindertest.model.world.Obstacle;

import com.google.inject.Inject;

public class VerticalWallGenerator implements ObstacleGenerator {

	private static String NAME = "Vertical wall";

	@Inject
	private Grid grid;

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void fill(int density) {
		int centerX = grid.getColumnCount() / 2;
		int centerY = grid.getRowCount() / 2;

		for (int row = 1; row <= grid.getRowCount(); row++) {
			if (isValidRow(row, centerY, density)) {
				Cell cell = grid.getCell(centerX, row);
				cell.setContent(Obstacle.create(cell));
			}
		}
	}

	private boolean isValidRow(int row, int center, int density) {
		density = (12 - density) / 2;
		return row >= center + density || row <= center - density;
	}
}
