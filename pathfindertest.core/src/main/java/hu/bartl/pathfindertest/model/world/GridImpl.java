package hu.bartl.pathfindertest.model.world;

import hu.bartl.pathfindertest.factory.LocationFactory;
import hu.bartl.pathfindertest.model.unit.Unit;

import java.util.List;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class GridImpl implements Grid {

	private static final int ROW_COUNT = 40;
	private static final int COLUMN_COUNT = 40;

	private final LocationFactory locationFactory;
	private final Provider<Cell> cellProvider;
	private final BiMap<Location, Cell> cells;

	@Inject
	private GridImpl(LocationFactory locationFactory,
			Provider<Cell> cellProvider) {
		this.locationFactory = locationFactory;
		this.cellProvider = cellProvider;
		cells = HashBiMap.create();
		initialize();
	};

	private void initialize() {
		for (int column = 1; column <= ROW_COUNT; column++) {
			for (int row = 1; row <= COLUMN_COUNT; row++) {
				Location location = locationFactory.create(column, row);
				cells.put(location, cellProvider.get());
			}
		}
	}

	@Override
	public Cell getCell(Location location) {
		Location loc = locationFactory.create(location.getXposition(),
				location.getYposition());
		return cells.get(loc);
	}

	@Override
	public Cell getCell(int x, int y) {
		return cells.get(LocationImpl.create(x, y));
	}

	public List<Cell> getNeighbours(Cell cell) {
		List<Cell> neighbours = Lists.newArrayList();
		Location cellLocation = cell.getLocation();
		for (Location location : getNeighbourLocations(cellLocation)) {
			Cell possibleNeighbour = cells.get(location);
			if (possibleNeighbour != null) {
				neighbours.add(possibleNeighbour);
			}
		}
		return neighbours;
	}

	@Override
	public Location getLocation(Cell cell) {
		return cells.inverse().get(cell);
	}

	private List<Location> getNeighbourLocations(Location location) {
		List<Location> neighbourLocations = Lists.newArrayList();
		List<Location> possibleLocations = locationFactory
				.createNeighbourLocations(location);

		for (Location possible : possibleLocations) {
			if (isValidLocation(possible)) {
				neighbourLocations.add(possible);
			}
		}
		return neighbourLocations;
	}

	private boolean isValidLocation(Location location) {
		return location.getXposition() >= 1
				&& location.getXposition() <= COLUMN_COUNT
				&& location.getYposition() >= 1
				&& location.getYposition() <= ROW_COUNT;
	}

	@Override
	public int getRowCount() {
		return ROW_COUNT;
	}

	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public List<Cell> getCells() {
		List<Cell> result = Lists.newArrayList();
		for (int row = 1; row <= ROW_COUNT; row++)
			for (int column = 1; column <= COLUMN_COUNT; column++) {
				{
					result.add(getCell(column, row));
				}
			}
		return result;
	}
	@Override
	public List<Unit> getUnits() {
		List<Unit> units = Lists.newArrayList();
		for (Cell cell : getCells()) {
			CellContent cellContent = cell.getContent();
			if (cellContent instanceof Unit) {
				units.add((Unit) cellContent);
			}
		}
		return units;
	}
}
