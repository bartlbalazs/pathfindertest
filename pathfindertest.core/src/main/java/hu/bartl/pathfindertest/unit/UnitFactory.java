package hu.bartl.pathfindertest.unit;

import hu.bartl.pathfindertest.model.unit.Unit;
import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.util.ColorModifier;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UnitFactory {

	@Inject
	private ColorModifier colorModifier;

	public Unit create(Cell cell, int groupID) {
		return UnitImpl.create(cell, groupID, colorModifier);
	}
}
