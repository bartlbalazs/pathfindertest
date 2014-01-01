package hu.bartl.pathfindertest.factory;

import hu.bartl.pathfindertest.model.world.Cell;
import hu.bartl.pathfindertest.model.world.CellContent;
import hu.bartl.pathfindertest.model.world.CellImpl;
import hu.bartl.pathfindertest.model.world.DirectionVector;
import hu.bartl.pathfindertest.model.world.DirectionVector.MovementVectorFactory;
import hu.bartl.pathfindertest.model.world.EmptyCellContent;
import hu.bartl.pathfindertest.model.world.Grid;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class CellProvider implements Provider<Cell> {

	@Inject
	private Grid grid;
	@Inject
	private Provider<DirectionVector> vectorProvider;
	@Inject
	private MovementVectorFactory movementVectorFactory;
	private Cell cellProxy;
	private CellInvocationHandler cellInvocationHandler;

	public CellProvider() {
		initializeProxy();
	}

	private void initializeProxy() {
		cellInvocationHandler = new CellInvocationHandler();
		cellProxy = (Cell) Proxy.newProxyInstance(Cell.class.getClassLoader(),
				new Class[]{Cell.class}, cellInvocationHandler);
	}

	@Override
	public Cell get() {
		final CellImpl cell = new CellImpl(movementVectorFactory,
				new EmptyContentProvider(), vectorProvider, grid);
		cellInvocationHandler.setInstance(cell);
		return cell;
	}

	private class CellInvocationHandler implements InvocationHandler {

		private Cell instance;

		public void setInstance(Cell instance) {
			this.instance = instance;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			return method.invoke(instance, args);
		}

	}

	private class EmptyContentProvider implements Provider<CellContent> {
		@Override
		public CellContent get() {
			return new EmptyCellContent(CellProvider.this.cellProxy);
		}
	}
}
