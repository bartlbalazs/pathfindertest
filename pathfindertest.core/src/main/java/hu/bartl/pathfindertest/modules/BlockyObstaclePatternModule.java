package hu.bartl.pathfindertest.modules;

import hu.bartl.pathfindertest.pattern.Block2X2;
import hu.bartl.pathfindertest.pattern.Block2X3;
import hu.bartl.pathfindertest.pattern.Block3X3;
import hu.bartl.pathfindertest.pattern.Block4X4;
import hu.bartl.pathfindertest.pattern.Block5X5;
import hu.bartl.pathfindertest.pattern.ObstaclePattern;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

public class BlockyObstaclePatternModule extends AbstractModule {

	@Override
	protected void configure() {
		Multibinder<ObstaclePattern> obstacleBinder = Multibinder.newSetBinder(
				binder(), ObstaclePattern.class, Names.named("Blocky"));

		obstacleBinder.addBinding().to(Block2X2.class);
		obstacleBinder.addBinding().to(Block2X3.class);
		obstacleBinder.addBinding().to(Block3X3.class);
		obstacleBinder.addBinding().to(Block4X4.class);
		obstacleBinder.addBinding().to(Block5X5.class);
	}

}
