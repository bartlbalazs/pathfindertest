package hu.bartl.pathfindertest.model;

import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.modules.CoreModule;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class CellImplTest {

	private Cell cell;

	@Before
	public void setup() {
		Injector i = Guice.createInjector(new CoreModule());
		cell = i.getInstance(Cell.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExceptionWhenNullContentSetted() {
		cell.setContent(null);
	}
}
