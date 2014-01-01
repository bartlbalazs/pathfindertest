package hu.bartl.pathfindertest.modules;

import hu.bartl.pathfindertest.algorithm.AStarV1;
import hu.bartl.pathfindertest.algorithm.AStarV2;
import hu.bartl.pathfindertest.algorithm.AStarWithDM;
import hu.bartl.pathfindertest.algorithm.DmDominant;
import hu.bartl.pathfindertest.algorithm.PathFinder;
import hu.bartl.pathfindertest.model.world.DirectionVector.MovementVectorFactory;
import hu.bartl.pathfindertest.model.world.MovementVectorFactoryImpl;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class AlgorithmModule extends AbstractModule {

	@Override
	protected void configure() {
		bindAlgorithmTypes();
		bindFactories();
	}

	private void bindAlgorithmTypes() {
		Multibinder<PathFinder> algorithmBinder = Multibinder.newSetBinder(
				binder(), PathFinder.class);

		algorithmBinder.addBinding().to(AStarV1.class);
		algorithmBinder.addBinding().to(AStarV2.class);
		algorithmBinder.addBinding().to(AStarWithDM.class);
		algorithmBinder.addBinding().to(DmDominant.class);
	}

	private void bindFactories() {
		bind(MovementVectorFactory.class).to(MovementVectorFactoryImpl.class);
	}
}
