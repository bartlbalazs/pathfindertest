package hu.bartl.pathfindertest.model.world;

public interface VectorLearner {
	void learn(DirectionVector vector);
	void learn(DirectionVector vector, LearnPriority priority);
	void reset();

	public static enum LearnPriority {
		PRIMARY, SECONDARY
	}
}
