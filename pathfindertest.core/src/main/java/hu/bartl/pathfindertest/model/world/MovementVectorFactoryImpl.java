package hu.bartl.pathfindertest.model.world;

import hu.bartl.pathfindertest.model.world.DirectionVector.MovementVectorFactory;

public class MovementVectorFactoryImpl implements MovementVectorFactory {

	@Override
	public DirectionVector create(Cell from, Cell to) {
		return MovementVector.create(from, to);
	}

}
