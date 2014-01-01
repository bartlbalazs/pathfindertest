package hu.bartl.pathfindertest.modules;

import hu.bartl.pathfindertest.controller.AlgorithmNameProvider;
import hu.bartl.pathfindertest.controller.AlgorithmProvider;
import hu.bartl.pathfindertest.controller.AlgorithmProviderImpl;
import hu.bartl.pathfindertest.controller.GridController;
import hu.bartl.pathfindertest.controller.GridControllerImpl;
import hu.bartl.pathfindertest.controller.ObstacleGeneratorNameProvider;
import hu.bartl.pathfindertest.controller.SimulationRunner;
import hu.bartl.pathfindertest.controller.SimulationRunnerImpl;
import hu.bartl.pathfindertest.factory.CellProvider;
import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.DirectionVector;
import hu.bartl.pathfindertest.model.world.DirectionVectorImpl;
import hu.bartl.pathfindertest.model.world.Grid;
import hu.bartl.pathfindertest.model.world.GridImpl;
import hu.bartl.pathfindertest.obstaclegenerator.FuzzyObstacleGenerator;
import hu.bartl.pathfindertest.obstaclegenerator.ObstacleGenerator;
import hu.bartl.pathfindertest.presenter.GridPresenter;
import hu.bartl.pathfindertest.presenter.GridPresenterImpl;
import hu.bartl.pathfindertest.presenter.SimulationRunnerPresenter;
import hu.bartl.pathfindertest.presenter.SimulationRunnerPresenterImpl;
import hu.bartl.pathfindertest.ui.MainUi;
import hu.bartl.pathfindertest.ui.MainUiImpl;

import java.util.List;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class CoreModule extends AbstractModule {

	@Override
	protected void configure() {
		installModules();
		bindModelImplementations();
		bindControllerImplementations();
		bindPresenters();
		bindFactories();
		bindInfrastructure();
	}

	private void installModules() {
		install(new ObsaclePatternModule());
		install(new AlgorithmModule());
	}

	private void bindModelImplementations() {
		bind(Grid.class).to(GridImpl.class);
		bind(Cell.class).toProvider(CellProvider.class);
		bind(DirectionVector.class).to(DirectionVectorImpl.class);
	}

	private void bindControllerImplementations() {
		bind(GridController.class).to(GridControllerImpl.class);
		bind(SimulationRunner.class).to(SimulationRunnerImpl.class);
	}

	private void bindPresenters() {
		bind(GridPresenter.class).to(GridPresenterImpl.class);
		bind(SimulationRunnerPresenter.class).to(
				SimulationRunnerPresenterImpl.class);
		bind(MainUi.class).to(MainUiImpl.class);
	}

	private void bindFactories() {
		bind(ObstacleGenerator.class).to(FuzzyObstacleGenerator.class);

	}

	private void bindInfrastructure() {
		bind(AlgorithmProvider.class).to(AlgorithmProviderImpl.class);
		bind(List.class).annotatedWith(Names.named("Algorithms")).toProvider(
				AlgorithmNameProvider.class);
		bind(List.class).annotatedWith(Names.named("ObstacleGenerators"))
				.toProvider(ObstacleGeneratorNameProvider.class);
	}
}
