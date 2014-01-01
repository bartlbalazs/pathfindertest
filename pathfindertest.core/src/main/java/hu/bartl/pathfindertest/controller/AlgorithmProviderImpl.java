package hu.bartl.pathfindertest.controller;

import hu.bartl.pathfindertest.algorithm.PathFinder;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class AlgorithmProviderImpl implements AlgorithmProvider {

	@Inject
	private Set<PathFinder> algorithms;

	public List<String> getAlgorithmNames() {
		List<String> result = Lists.newArrayList();
		for (PathFinder pf : algorithms) {
			result.add(pf.getClass().getSimpleName());
		}
		Collections.sort(result);
		return result;
	}

	public Class<? extends PathFinder> getAlgorithmTypeByName(String name) {
		for (PathFinder pf : algorithms) {
			Class<? extends PathFinder> algorithm = pf.getClass();
			if (algorithm.getSimpleName().equals(name)) {
				return algorithm;
			}
		}
		return null;
	}

	@Override
	public Class<? extends PathFinder> getDefaultAlgorithmType() {
		return getAlgorithmTypeByName(getAlgorithmNames().get(0));
	}
}
