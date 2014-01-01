package hu.bartl.pathfindertest.model.world;

public class MovementVector implements DirectionVector {

	private final double x;
	private final double y;
	private final double magnitude;

	public static MovementVector create(Cell from, Cell to) {
		Location fromLocation = from.getLocation();
		Location toLocation = to.getLocation();

		int moveVectorX = toLocation.getXposition()
				- fromLocation.getXposition();
		int moveVectorY = toLocation.getYposition()
				- fromLocation.getYposition();
		double moveVectorMagnitude = (from.getLocation().equals(to
				.getLocation())) ? 0 : 1;

		return new MovementVector(moveVectorX, moveVectorY, moveVectorMagnitude);
	}

	private MovementVector(double x, double y, double magnitude) {
		super();
		this.x = x;
		this.y = y;
		this.magnitude = magnitude;
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public double getMagnitude() {
		return this.magnitude;
	}

	@Override
	public void learn(DirectionVector vector) {
	}

	@Override
	public void learn(DirectionVector vector, LearnPriority priority) {
	}

	@Override
	public void reset() {

	}

	@Override
	public double scalarProduct(DirectionVector vector) {
		return x * vector.getX() + y * vector.getY();
	}

}
