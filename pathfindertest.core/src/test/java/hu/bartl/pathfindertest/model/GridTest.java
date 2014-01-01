package hu.bartl.pathfindertest.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.Grid;
import hu.bartl.pathfindertest.model.world.Location;
import hu.bartl.pathfindertest.modules.CoreModule;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GridTest {

	private static final int X_POSITION = 5;
	private static final int Y_POSITION = 6;

	@Mock
	private Location location;
	@Mock
	private Location neighbourLocation;
	private Grid grid;

	@Before
	public void setup() {
		initMocks(this);
		when(location.getXposition()).thenReturn(X_POSITION);
		when(location.getYposition()).thenReturn(Y_POSITION);
		when(neighbourLocation.getXposition()).thenReturn(X_POSITION);
		when(neighbourLocation.getYposition()).thenReturn(X_POSITION);
		Injector i = Guice.createInjector(new CoreModule());
		grid = i.getInstance(Grid.class);
	}

	@Test
	public void testGetCell() {
		Cell cellByLocation = grid.getCell(location);
		Cell cellByCoordinates = grid.getCell(X_POSITION, Y_POSITION);
		assertNotNull(cellByLocation);
		assertNotNull(cellByCoordinates);
		assertEquals(cellByLocation, cellByCoordinates);
	}

	@Test
	public void testNeighbours() {
		Cell cellByLocation = grid.getCell(location);
		Cell neighbour = grid.getCell(neighbourLocation);
		assertTrue(grid.getNeighbours(cellByLocation).contains(neighbour));
	}

	@Test
	public void testGetLocation() {
		Cell cellByLocation = grid.getCell(location);
		Location location2 = cellByLocation.getLocation();
		assertEquals(location.getXposition(), location2.getXposition());
		assertEquals(location.getYposition(), location2.getYposition());
	}
}
