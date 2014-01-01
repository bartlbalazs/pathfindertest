package hu.bartl.pathfindertest.presenter;

import hu.bartl.pathfindertest.model.world.Cell;

public interface GridPresenter {
	void initializeLayout(int rows, int columns);
	void createCellPresenterFor(Cell cell);
	void cellLeftClicked(CellPresenter cellPresenter, int modifier);
	void cellLeftCtrlClicked(CellPresenter cellPresenter);
	void cellRightClicked(CellPresenter cellPresenter);
	void cellRightCtrlClicked(CellPresenter cellPresenter, int modifier);
}
