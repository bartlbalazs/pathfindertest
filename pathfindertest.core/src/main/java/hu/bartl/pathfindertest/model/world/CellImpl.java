package hu.bartl.pathfindertest.model.world;

import hu.bartl.pathfindertest.model.world.DirectionVector.MovementVectorFactory;

import java.awt.Color;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Provider;

public class CellImpl implements Cell {

	private final Grid grid;
	private final CellContent emptyContent;
	private CellContent content;
	private final DirectionVector directionVector;
	private final MovementVectorFactory movementVectorFactory;
	private Cell.ContentChangedListener changeListener;

	public CellImpl(MovementVectorFactory movementVectorFactory,
			Provider<CellContent> emptyContentProvider,
			Provider<DirectionVector> directionVectorProvider, Grid grid) {
		this.movementVectorFactory = movementVectorFactory;
		this.emptyContent = emptyContentProvider.get();
		this.content = emptyContent;
		this.directionVector = directionVectorProvider.get();
		this.grid = grid;
	}

	@Override
	public List<Cell> getNeighbours() {
		return grid.getNeighbours(this);
	}

	@Override
	public List<Cell> getEmptyNeighbours() {
		List<Cell> emptyNeighbours = Lists.newLinkedList();
		for (Cell cell : getNeighbours()) {
			if (cell.isEmpty()) {
				emptyNeighbours.add(cell);
			}
		}
		return emptyNeighbours;
	}

	@Override
	public Location getLocation() {
		return grid.getLocation(this);
	}

	@Override
	public CellContent getContent() {
		return content;
	}

	@Override
	public void setContent(CellContent content) {
		if (content == null) {
			throw new IllegalArgumentException();
		}
		this.content = content;
		fireContentChanged();
	}

	@Override
	public void clearContent() {
		this.content = emptyContent;
		fireContentChanged();
	}

	private void fireContentChanged() {
		if (changeListener != null) {
			changeListener.onContentChanged();
		}
	}

	@Override
	public boolean isBlocked() {
		return content.isBlocking();
	}

	@Override
	public Color getColor() {
		return content.getColor();
	}

	@Override
	public boolean isEmpty() {
		return !content.isBlocking();
	}

	@Override
	public void addContentChangedListener(ContentChangedListener listener) {
		this.changeListener = listener;
	}

	@Override
	public double getDistanceFrom(Cell cell) {
		return grid.getLocation(this).getDistanceFrom(grid.getLocation(cell));
	}

	@Override
	public List<Cell> getClosestEmptyCells() {
		if (this.isEmpty()) {
			List<Cell> result = Lists.newArrayList();
			result.add(this);
			return result;
		} else {
			List<Cell> thisCell = Lists.newArrayList();
			thisCell.add(this);
			return getClosestEmptyCells(thisCell);
		}
	}

	private List<Cell> getClosestEmptyCells(List<Cell> cells) {
		List<Cell> result = Lists.newArrayList();
		List<Cell> neighbours = Lists.newArrayList();
		for (Cell cell : cells) {
			result.addAll(cell.getEmptyNeighbours());
			neighbours.addAll(cell.getNeighbours());
		}
		if (result.isEmpty()) {
			neighbours.removeAll(cells);
			return getClosestEmptyCells(neighbours);
		} else {
			return result;
		}
	}

	@Override
	public void learn(DirectionVector vector) {
		learn(vector, LearnPriority.PRIMARY);
	}

	@Override
	public void learn(DirectionVector vector, LearnPriority priority) {
		directionVector.learn(vector, priority);
		if (priority == LearnPriority.PRIMARY) {
			for (Cell neighbour : getNeighbours()) {
				neighbour.learn(vector, LearnPriority.SECONDARY);
			}
		}
		fireContentChanged();
	}

	@Override
	public DirectionVector getDirectionVector() {
		return this.directionVector;
	}

	@Override
	public DirectionVector getDirectionVectorTo(Cell cell) {
		return movementVectorFactory.create(this, cell);
	}

	@Override
	public void reset() {
		directionVector.reset();
		fireContentChanged();
	}
}
