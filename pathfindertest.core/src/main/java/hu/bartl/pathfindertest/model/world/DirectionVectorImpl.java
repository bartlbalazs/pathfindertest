package hu.bartl.pathfindertest.model.world;

public class DirectionVectorImpl implements DirectionVector {

	private static final double LEARNING_RATE = 0.3;
	private double x = 0;
	private double y = 0;

	@Override
	public void learn(DirectionVector vector) {
		learn(vector, LearnPriority.PRIMARY);
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void learn(DirectionVector vector, LearnPriority priority) {
		calculate(vector, priority);
		normalize();
	}

	private void calculate(DirectionVector vector, LearnPriority priority) {
		double learningRate = (priority == LearnPriority.PRIMARY)
				? LEARNING_RATE
				: LEARNING_RATE / 3;

		x = (1 - learningRate) * x + learningRate * vector.getX();
		y = (1 - learningRate) * y + learningRate * vector.getY();
	}

	private void normalize() {
		double magnitude = getMagnitude();
		if (magnitude > 1) {
			x /= magnitude;
			y /= magnitude;
		}
	}

	public double getMagnitude() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	@Override
	public void reset() {
		x = y = 0;
	}

	@Override
	public double scalarProduct(DirectionVector vector) {
		return x * vector.getX() + y * vector.getY();
	}

}
