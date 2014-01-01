package hu.bartl.pathfindertest.model.world;

import hu.bartl.pathfindertest.model.world.Location;

public class LocationImpl implements Location {

	private final int x;
	private final int y;

	public static LocationImpl create(int x, int y) {
		return new LocationImpl(x, y);
	}

	private LocationImpl(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getXposition() {
		return x;
	}

	@Override
	public int getYposition() {
		return y;
	}

	@Override
	public double getDistanceFrom(Location location) {
		if (location == null) {
			return Double.MAX_VALUE;
		}
		return Math.sqrt(Math.pow(
				location.getXposition() - this.getXposition(), 2)
				+ Math.pow(location.getYposition() - this.getYposition(), 2));
	}

	@Override
	public String toString() {
		return "LocationImpl [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationImpl other = (LocationImpl) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
