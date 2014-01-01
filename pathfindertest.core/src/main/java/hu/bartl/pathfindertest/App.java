package hu.bartl.pathfindertest;

import hu.bartl.pathfindertest.modules.CoreModule;
import hu.bartl.pathfindertest.ui.MainUi;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class App {
	public static void main(String[] args) {
		Injector i = Guice.createInjector(new CoreModule());
		MainUi ui = i.getInstance(MainUi.class);
		ui.start();
	}
}
