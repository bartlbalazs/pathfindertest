package hu.bartl.pathfindertest.modules;

import hu.bartl.pathfindertest.obstaclegenerator.BlockyObstacleGenerator;
import hu.bartl.pathfindertest.obstaclegenerator.FuzzyObstacleGenerator;
import hu.bartl.pathfindertest.obstaclegenerator.ObstacleGenerator;
import hu.bartl.pathfindertest.obstaclegenerator.VerticalWallGenerator;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class ObsaclePatternModule extends AbstractModule {

	@Override
	protected void configure() {
		installModules();
		bindGenerators();
	}

	private void installModules() {
		install(new FuzzyObstaclePatternModule());
		install(new BlockyObstaclePatternModule());
	}

	private void bindGenerators() {
		Multibinder<ObstacleGenerator> generatorBinder = Multibinder
				.newSetBinder(binder(), ObstacleGenerator.class);

		generatorBinder.addBinding().to(BlockyObstacleGenerator.class);
		generatorBinder.addBinding().to(FuzzyObstacleGenerator.class);
		generatorBinder.addBinding().to(VerticalWallGenerator.class);
	}
}
