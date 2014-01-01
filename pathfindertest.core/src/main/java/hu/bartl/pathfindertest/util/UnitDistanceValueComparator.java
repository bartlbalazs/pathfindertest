package hu.bartl.pathfindertest.util;

import hu.bartl.pathfindertest.model.unit.Unit;

import java.util.Comparator;
import java.util.Map;

public class UnitDistanceValueComparator implements Comparator<Unit> {
	private Map<Unit, Double> base;

	public UnitDistanceValueComparator(Map<Unit, Double> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with
	// equals.
	public int compare(Unit a, Unit b) {
		if (base.get(a) <= base.get(b)) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}
}