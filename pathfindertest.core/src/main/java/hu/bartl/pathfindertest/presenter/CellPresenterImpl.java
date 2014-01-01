package hu.bartl.pathfindertest.presenter;

import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.DirectionVector;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class CellPresenterImpl extends JButton implements CellPresenter {

	private static final long serialVersionUID = -112198442711588244L;

	private final Cell cell;
	private final GridPresenter gridPresenter;

	private CellPresenterImpl(Cell cell, final GridPresenter gridPresenter) {
		this.cell = cell;
		this.gridPresenter = gridPresenter;
		addClickListener();
		repaint();
	}

	private void addClickListener() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					handleLeftClick(e);
				}
				if (SwingUtilities.isRightMouseButton(e)) {
					handleRightClick(e);
				}
			}

		});
	}

	private void handleLeftClick(MouseEvent e) {
		if (isCtrlModified(e)) {
			gridPresenter.cellLeftCtrlClicked(CellPresenterImpl.this);
		} else {
			gridPresenter.cellLeftClicked(CellPresenterImpl.this,
					e.getModifiers());
		}
		repaint();
	}
	private void handleRightClick(MouseEvent e) {
		if (isCtrlModified(e)) {
			gridPresenter.cellRightCtrlClicked(CellPresenterImpl.this,
					e.getModifiers());
		} else {
			gridPresenter.cellRightClicked(CellPresenterImpl.this);
		}
		repaint();
	}

	private boolean isCtrlModified(MouseEvent e) {
		return (e.getModifiers() & InputEvent.CTRL_MASK) != 0;
	}
	public static CellPresenterImpl create(Cell cell,
			GridPresenter gridPresenter) {
		return new CellPresenterImpl(cell, gridPresenter);
	}

	public Cell getCell() {
		return this.cell;
	}

	@Override
	public void repaint() {
		if (cell != null) {
			super.repaint();
			setBackground(cell.getColor());
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		DirectionVector vector = cell.getDirectionVector();
		if (vector.getMagnitude() > 0) {
			Rectangle clipBounds = g.getClipBounds();
			int centerX = (int) clipBounds.getCenterX();
			int centerY = (int) clipBounds.getCenterY();
			int maxX = (int) (clipBounds.getMaxX() / 2);
			int x = centerX + (int) (vector.getX() * maxX);
			int maxY = (int) (clipBounds.getMaxY() / 2);
			int y = centerY + (int) (vector.getY() * maxY);
			g.drawLine(centerX, centerY, x, y);
		}
	}
	@Override
	public void onContentChanged() {
		repaint();
	}

	@Override
	public void refResh() {
		repaint();
	}

}
