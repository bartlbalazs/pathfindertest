package hu.bartl.pathfindertest.model.world;

import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.CellContent;

import java.awt.Color;

public class EmptyCellContent implements CellContent {

	private final Cell cell;

	public EmptyCellContent(Cell cell) {
		this.cell = cell;
	}

	@Override
	public Cell getCell() {
		return cell;
	}

	@Override
	public Color getColor() {
		return Color.WHITE;
	}

	@Override
	public boolean isBlocking() {
		return false;
	}
}
