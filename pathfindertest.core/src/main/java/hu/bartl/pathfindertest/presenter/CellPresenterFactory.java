package hu.bartl.pathfindertest.presenter;

import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.presenter.CellPresenter;
import hu.bartl.pathfindertest.presenter.GridPresenter;

import com.google.inject.Inject;

public class CellPresenterFactory {

	private final GridPresenter gridPresenter;
	@Inject
	public CellPresenterFactory(GridPresenter gridPresenter) {
		this.gridPresenter = gridPresenter;
	}

	public CellPresenter create(Cell cell) {
		CellPresenterImpl cellPresenter = CellPresenterImpl.create(cell,
				gridPresenter);
		cell.addContentChangedListener(cellPresenter);
		return cellPresenter;
	}
}
