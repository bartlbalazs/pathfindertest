package hu.bartl.pathfindertest.model.world;

import hu.bartl.pathfindertest.util.ColorModifier;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TargetFactory {

	@Inject
	private ColorModifier colorModifier;

	public Target create(Cell cell, int groupID) {
		return Target.create(cell, groupID, colorModifier);
	}

}
