package hu.bartl.pathfindertest.model.factory;

import static org.junit.Assert.assertNotNull;
import hu.bartl.pathfindertest.factory.CellProvider;
import hu.bartl.pathfindertest.model.world.Cell;

import org.junit.Before;
import org.junit.Test;

public class CellProviderTest {

	private CellProvider cellProvider;

	@Before
	public void setup() {
		cellProvider = new CellProvider();
	}

	@Test
	public void testGetNotNull() throws Exception {
		Cell cell = cellProvider.get();
		assertNotNull(cell);
	}

	@Test
	public void testGettedCellContentNotNull() {
		Cell cell = cellProvider.get();
		assertNotNull(cell.getContent());
	}

}
