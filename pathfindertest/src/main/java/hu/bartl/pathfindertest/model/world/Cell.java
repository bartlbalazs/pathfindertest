package hu.bartl.pathfindertest.model.world;

import java.awt.Color;
import java.util.List;

public interface Cell extends VectorLearner {
	List<Cell> getNeighbours();
	List<Cell> getEmptyNeighbours();
	Location getLocation();
	CellContent getContent();
	List<Cell> getClosestEmptyCells();
	double getDistanceFrom(Cell cell);
	void setContent(CellContent content);
	void clearContent();
	boolean isBlocked();
	boolean isEmpty();
	Color getColor();
	void addContentChangedListener(ContentChangedListener listener);
	DirectionVector getDirectionVector();
	DirectionVector getDirectionVectorTo(Cell cell);

	public static interface ContentChangedListener {
		void onContentChanged();
	}
}
