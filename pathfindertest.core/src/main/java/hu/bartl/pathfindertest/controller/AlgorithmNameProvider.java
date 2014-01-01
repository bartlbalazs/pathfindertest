package hu.bartl.pathfindertest.controller;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class AlgorithmNameProvider implements Provider<List<String>> {

	@Inject
	private AlgorithmProvider aP;

	@Override
	public List<String> get() {
		return aP.getAlgorithmNames();
	}

}
