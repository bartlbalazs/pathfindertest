package hu.bartl.pathfindertest.model.world;

import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.CellContent;

import java.awt.Color;

public class Obstacle implements CellContent {

	private final Cell cell;

	public static Obstacle create(Cell cell) {
		return new Obstacle(cell);
	}

	private Obstacle(Cell cell) {
		this.cell = cell;
	}

	@Override
	public Cell getCell() {
		return this.cell;
	}

	@Override
	public Color getColor() {
		return Color.RED;
	}

	@Override
	public boolean isBlocking() {
		return true;
	}
}
