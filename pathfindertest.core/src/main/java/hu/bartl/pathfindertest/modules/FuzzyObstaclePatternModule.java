package hu.bartl.pathfindertest.modules;

import hu.bartl.pathfindertest.pattern.BackShlash;
import hu.bartl.pathfindertest.pattern.Cross;
import hu.bartl.pathfindertest.pattern.Fuzzy;
import hu.bartl.pathfindertest.pattern.HLine;
import hu.bartl.pathfindertest.pattern.ObstaclePattern;
import hu.bartl.pathfindertest.pattern.SPattern;
import hu.bartl.pathfindertest.pattern.Slash;
import hu.bartl.pathfindertest.pattern.TPattern;
import hu.bartl.pathfindertest.pattern.Triangle;
import hu.bartl.pathfindertest.pattern.VLine;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

public class FuzzyObstaclePatternModule extends AbstractModule {

	@Override
	protected void configure() {
		Multibinder<ObstaclePattern> obstacleBinder = Multibinder.newSetBinder(
				binder(), ObstaclePattern.class, Names.named("Fuzzy"));

		obstacleBinder.addBinding().to(Cross.class);
		obstacleBinder.addBinding().to(HLine.class);
		obstacleBinder.addBinding().to(VLine.class);
		obstacleBinder.addBinding().to(TPattern.class);
		obstacleBinder.addBinding().to(SPattern.class);
		obstacleBinder.addBinding().to(Fuzzy.class);
		obstacleBinder.addBinding().to(Triangle.class);
		obstacleBinder.addBinding().to(Slash.class);
		obstacleBinder.addBinding().to(BackShlash.class);
	}
}
