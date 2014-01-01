package hu.bartl.pathfindertest.model.world;

public interface Location {
	int getXposition();
	int getYposition();
	double getDistanceFrom(Location location);
}
