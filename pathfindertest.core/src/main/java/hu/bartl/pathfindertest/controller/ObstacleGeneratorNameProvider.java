package hu.bartl.pathfindertest.controller;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ObstacleGeneratorNameProvider implements Provider<List<String>> {

	@Inject
	private ObstacleGeneratorProvider gp;

	@Override
	public List<String> get() {
		return gp.getGeneratormNames();
	}

}
