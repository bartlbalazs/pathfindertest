package hu.bartl.pathfindertest.model.world;

import hu.bartl.pathfindertest.util.ColorModifier;

import java.awt.Color;

public class Target implements CellContent {

	private final Cell cell;
	private final int groupID;
	private final ColorModifier colorModifier;
	private static final Color BASE_COLOR = Color.YELLOW;

	public static Target create(Cell cell, int groupID,
			ColorModifier colorModifier) {
		return new Target(cell, groupID, colorModifier);
	}

	private Target(Cell cell, int groupID, ColorModifier colorModifier) {
		this.cell = cell;
		this.groupID = groupID;
		this.colorModifier = colorModifier;
	}

	@Override
	public Cell getCell() {
		return this.cell;
	}

	@Override
	public Color getColor() {
		return colorModifier.modifyColor(BASE_COLOR, groupID);
	}

	@Override
	public boolean isBlocking() {
		return false;
	}

	public int getGroupID() {
		return this.groupID;
	}

}
