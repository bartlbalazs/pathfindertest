package hu.bartl.pathfindertest.controller;

import hu.bartl.pathfindertest.obstaclegenerator.ObstacleGenerator;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class ObstacleGeneratorProvider {

	@Inject
	private Set<ObstacleGenerator> generators;

	public List<String> getGeneratormNames() {
		List<String> result = Lists.newArrayList();
		for (ObstacleGenerator g : generators) {
			result.add(g.getName());
		}
		Collections.sort(result);
		return result;
	}

	public ObstacleGenerator getGeneratorTypeByName(String name) {
		for (ObstacleGenerator g : generators) {
			if (g.getName().equals(name)) {
				return g;
			}
		}
		return null;
	}

	public String getDefaultGeneratorName() {
		return getGeneratormNames().get(0);
	}
}
