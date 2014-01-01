package hu.bartl.pathfindertest.model.world;

import java.awt.Color;

public interface CellContent {
	Cell getCell();
	Color getColor();
	boolean isBlocking();
}
