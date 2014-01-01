package hu.bartl.pathfindertest.presenter;

import hu.bartl.pathfindertest.model.world.Cell;

public interface CellPresenter extends Cell.ContentChangedListener {
	Cell getCell();
	void refResh();
}
