package hu.bartl.pathfindertest.model.unit;

public interface MoveNotifier {
	void adddMoveListener(MoveListener listener);
	void removeMoveListener(MoveListener listener);
}
