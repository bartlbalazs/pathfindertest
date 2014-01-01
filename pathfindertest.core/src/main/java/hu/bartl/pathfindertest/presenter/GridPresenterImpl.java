package hu.bartl.pathfindertest.presenter;

import hu.bartl.pathfindertest.controller.GridController;
import hu.bartl.pathfindertest.model.world.Cell;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GridPresenterImpl extends JPanel implements GridPresenter {
	private static final long serialVersionUID = -338072124449904218L;

	@Inject
	private CellPresenterFactory cellPresenterFactory;
	@Inject
	private GridController gridController;

	@Override
	public void initializeLayout(int rows, int columns) {
		setPreferredSize(new Dimension(500, 600));
		setBackground(Color.LIGHT_GRAY);
		setLayout(new GridLayout(rows, columns));
	}

	@Override
	public void createCellPresenterFor(Cell cell) {
		add((Component) cellPresenterFactory.create(cell));
	}

	@Override
	public void cellLeftClicked(CellPresenter cellPresenter, int modifier) {
		gridController.onPrimaryInteraction(cellPresenter.getCell(), modifier);
	}

	@Override
	public void cellLeftCtrlClicked(CellPresenter cellPresenter) {
		gridController.onModifiedPrimaryInteraction(cellPresenter.getCell());
	}

	@Override
	public void cellRightClicked(CellPresenter cellPresenter) {
		gridController.onSecondaryInteraction(cellPresenter.getCell());
	}

	@Override
	public void cellRightCtrlClicked(CellPresenter cellPresenter, int modifier) {
		gridController.onModifiedSecondaryInteraction(cellPresenter.getCell(),
				modifier);
	}
}
