package hu.bartl.pathfindertest.presenter;

import hu.bartl.pathfindertest.controller.SimulationRunner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class SimulationRunnerPresenterImpl extends JPanel
		implements
			SimulationRunnerPresenter {
	private static final long serialVersionUID = -7440330139091489162L;

	private final SimulationRunner simulationRunner;

	private final JLabel iterationLabel = new JLabel("0");
	private final JLabel failedMoveLabel = new JLabel("0");
	private final JLabel averagePathLengthLabel = new JLabel("0");
	private final JLabel unitCountLabel = new JLabel("0");
	private final JComboBox<String> obstacleCB = new JComboBox<>();
	private final JComboBox<String> algorithmCB = new JComboBox<>();
	private final List<String> algorithmNames;
	private final List<String> obstacleGeneratorNames;

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Inject
	public SimulationRunnerPresenterImpl(SimulationRunner simulationRunner,
			@Named("Algorithms") Provider<List> algorithmNameProvider,
			@Named("ObstacleGenerators") Provider<List> generatorNameProvider) {
		this.simulationRunner = simulationRunner;
		this.algorithmNames = algorithmNameProvider.get();
		this.obstacleGeneratorNames = generatorNameProvider.get();
		initialize();
	}

	private void initialize() {
		initializePanel();
		addButtons();
		addSpeedSlider();
		addObstaclePanel();
		addAlgorithmSelection();
		addInfoPanel();
		addHelpPanel();
	}

	private void initializePanel() {
		setPreferredSize(new Dimension(340, 600));
	}

	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		GridLayout layout = new GridLayout(1, 4);
		layout.setHgap(3);
		buttonPanel.setLayout(layout);
		buttonPanel.setPreferredSize(new Dimension(320, 30));
		buttonPanel.add(buildStartButton());
		buttonPanel.add(buildStopButton());
		buttonPanel.add(buildResetButton());
		buttonPanel.add(buildClearButton());
		add(buttonPanel);
	}

	private JButton buildStartButton() {
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simulationRunner.start();
			}
		});
		return startButton;
	}

	private JButton buildStopButton() {
		JButton startButton = new JButton("Stop");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simulationRunner.stop();
			}
		});
		return startButton;
	}

	private JButton buildResetButton() {
		JButton startButton = new JButton("Reset");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simulationRunner.reset();
			}
		});
		return startButton;
	}

	private JButton buildClearButton() {
		JButton startButton = new JButton("Clear");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simulationRunner.clear();
			}
		});
		return startButton;
	}

	private void addSpeedSlider() {
		add(new JLabel("Simulation speed (iterations/sec)"));
		final JSlider slider = new JSlider();
		slider.setPreferredSize(new Dimension(320, 50));
		slider.setMinimum(1);
		slider.setMaximum(50);

		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		labelTable.put(new Integer(1), new JLabel("0.1"));
		labelTable.put(new Integer(10), new JLabel("1.0"));
		labelTable.put(new Integer(20), new JLabel("2.0"));
		labelTable.put(new Integer(30), new JLabel("3.0"));
		labelTable.put(new Integer(40), new JLabel("4.0"));
		labelTable.put(new Integer(50), new JLabel("5.0"));
		slider.setLabelTable(labelTable);

		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		slider.setValue(10);

		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				simulationRunner.setSpeed((slider.getValue() / 10f));
			}
		});

		add(slider);
	}

	private void addObstaclePanel() {
		JPanel obstaclePanel = new JPanel();
		obstaclePanel.setPreferredSize(new Dimension(320, 130));
		obstaclePanel.add(new JLabel("Obstacles"));
		initializeObstacleSelector();
		obstaclePanel.add(obstacleCB);

		obstaclePanel.add(new JLabel("Obstacle density"));
		final JSlider slider = new JSlider();
		slider.setPreferredSize(new Dimension(230, 50));
		slider.setMinimum(1);
		slider.setMaximum(10);

		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		slider.setValue(5);

		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				simulationRunner.setObstacleDensity(slider.getValue());
			}
		});

		obstaclePanel.add(slider, BorderLayout.WEST);

		JButton generateBtn = new JButton("Build");
		generateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simulationRunner.generateObstacles();
			}
		});
		generateBtn.setPreferredSize(new Dimension(80, 30));
		obstaclePanel.add(generateBtn, BorderLayout.EAST);
		add(obstaclePanel);
	}

	private void initializeObstacleSelector() {
		obstacleCB.setPreferredSize(new Dimension(320, 30));
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (String item : obstacleGeneratorNames) {
			model.addElement(item);
		}
		obstacleCB.setModel(model);
		obstacleCB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				String name = (String) cb.getSelectedItem();
				simulationRunner.setObstacleGeneratorName(name);
			}
		});
	}

	private void addAlgorithmSelection() {
		add(new JLabel("Algorithm"));
		algorithmCB.setPreferredSize(new Dimension(320, 30));
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (String item : algorithmNames) {
			model.addElement(item);
		}
		algorithmCB.setModel(model);
		algorithmCB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				String algorithmName = (String) cb.getSelectedItem();
				simulationRunner.setAlgorithmType(algorithmName);
			}
		});
		add(algorithmCB);

		final JCheckBox stepClosestCB = new JCheckBox();
		stepClosestCB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				simulationRunner.setStepWithClosest(stepClosestCB.isSelected());
			}
		});
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(320, 30));
		panel.setLayout(new GridLayout(1, 2));
		panel.add(new JLabel("Step with closest:"));
		panel.add(stepClosestCB);
		add(panel);
	}

	private void addInfoPanel() {
		add(new JLabel("Statistics"));
		JPanel infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(320, 60));
		GridLayout layout = new GridLayout(4, 2);
		infoPanel.setLayout(layout);
		infoPanel.add(new JLabel("Unit count: "));
		infoPanel.add(unitCountLabel);
		infoPanel.add(new JLabel("Iteration count: "));
		infoPanel.add(iterationLabel);
		infoPanel.add(new JLabel("Failed move count: "));
		infoPanel.add(failedMoveLabel);
		infoPanel.add(new JLabel("Average path length: "));
		infoPanel.add(averagePathLengthLabel);
		add(infoPanel);
	}

	private void addHelpPanel() {
		add(new JLabel("Controls"));
		JPanel helpPanel = new JPanel();
		helpPanel.setPreferredSize(new Dimension(320, 90));
		GridLayout layout = new GridLayout(6, 2);
		helpPanel.setLayout(layout);
		helpPanel.add(new JLabel("New obstacle: "));
		helpPanel.add(new JLabel("Right click"));
		helpPanel.add(new JLabel("Clear cell: "));
		helpPanel.add(new JLabel("Ctrl+Left click"));
		helpPanel.add(new JLabel("New unit (Group 1): "));
		helpPanel.add(new JLabel("Left click"));
		helpPanel.add(new JLabel("New unit (Group 2): "));
		helpPanel.add(new JLabel("Shift+Left click"));
		helpPanel.add(new JLabel("Place target (Gr. 1): "));
		helpPanel.add(new JLabel("Ctrl+Right click"));
		helpPanel.add(new JLabel("Place target (Gr. 2): "));
		helpPanel.add(new JLabel("Shift+Ctrl+Right click"));
		add(helpPanel, BorderLayout.SOUTH);
	}

	@Override
	public void setIterationCount(int iterationCount) {
		iterationLabel.setText(Integer.toString(iterationCount));
	}

	@Override
	public void setFailedMoveCount(double failedMoveCount) {
		failedMoveLabel.setText(String.format("%.2f", failedMoveCount));
	}
	@Override
	public void setAveragePathLength(double pathLength) {
		averagePathLengthLabel.setText(String.format("%.2f", pathLength));
	}

	@Override
	public void setUnitCount(int unitCount) {
		unitCountLabel.setText(Integer.toString(unitCount));
	}
}
