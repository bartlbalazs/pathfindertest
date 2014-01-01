package hu.bartl.pathfindertest.ui;

import hu.bartl.pathfindertest.presenter.GridPresenter;
import hu.bartl.pathfindertest.presenter.SimulationRunnerPresenter;
import hu.bartl.pathfindertest.util.VersionNumberLoader;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.google.inject.Inject;

public class MainUiImpl extends JFrame implements MainUi {

	private static final long serialVersionUID = 2952150132036717439L;

	private static final String APPLICATION_NAME = "PathFinderTest";
	private static final int WIDTH = 845;
	private static final int HEIGHT = 600;

	private final GridPresenter gridPresenter;
	private final SimulationRunnerPresenter simulationRunnerPresenter;
	private final VersionNumberLoader versionNumberRetreiever;

	@Inject
	public MainUiImpl(GridPresenter gridPresenter,
			SimulationRunnerPresenter simulationRunnerPresenter,
			VersionNumberLoader versionNumberLoader) {
		this.gridPresenter = gridPresenter;
		this.simulationRunnerPresenter = simulationRunnerPresenter;
		this.versionNumberRetreiever = versionNumberLoader;
		initialize();
	}

	private void initialize() {
		initializeFrame();
		addGridPresenter();
		addSimulationRunnerPresenter();
	}

	private void initializeFrame() {
		setTitle(APPLICATION_NAME + " v"
				+ versionNumberRetreiever.getVersionNumber());
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void addGridPresenter() {
		add((Component) gridPresenter, BorderLayout.LINE_START);
	}

	private void addSimulationRunnerPresenter() {
		add((Component) simulationRunnerPresenter, BorderLayout.LINE_END);
	}

	@Override
	public void start() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainUiImpl.this.setVisible(true);
			}
		});

	}
}
