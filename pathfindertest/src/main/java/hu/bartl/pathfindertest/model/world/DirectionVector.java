package hu.bartl.pathfindertest.model.world;

public interface DirectionVector extends VectorLearner {
	double getX();
	double getY();
	double getMagnitude();
	double scalarProduct(DirectionVector vector);

	public static interface MovementVectorFactory {
		DirectionVector create(Cell from, Cell to);
	}
}
