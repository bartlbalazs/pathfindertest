package hu.bartl.pathfindertest.controller;

import hu.bartl.pathfindertest.algorithm.PathFinder;

import java.util.List;

public interface AlgorithmProvider {
	List<String> getAlgorithmNames();
	Class<? extends PathFinder> getAlgorithmTypeByName(String name);
	Class<? extends PathFinder> getDefaultAlgorithmType();
}
