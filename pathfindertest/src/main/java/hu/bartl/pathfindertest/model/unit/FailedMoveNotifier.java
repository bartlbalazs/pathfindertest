package hu.bartl.pathfindertest.model.unit;

public interface FailedMoveNotifier {
	void addFailedMoveListener(FailedMoveListener listener);
	void removeFailedMoveListener(FailedMoveListener listener);
}
