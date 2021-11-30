package controller.impl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import task.impl.DeisotopingWorker;
import view.impl.CoverPanel;
import view.impl.DeisotopingPanel;
import view.impl.MenuBar;
import view.impl.NavigationPanel;
import view.impl.ResultsPanel;

public class MainFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = -2547880461104225298L;

	private static final double VERSION   = 1.00;
	private static final int FRAME_WIDTH  = 573;
	private static final int FRAME_HEIGHT = 534;

	private MenuBar menuBar;
	
	private JSplitPane splitPane;
	private CoverPanel coverPanel;
	private NavigationPanel navigationPanel;

	public MainFrame() {
		super();

		initUI();
		layoutUI();
	}
	
	public JSplitPane getSplitPane() {
		return splitPane;
	}

	public CoverPanel getCoverPanel() {
		return coverPanel;
	}

	public NavigationPanel getNavigationPanel() {
		return navigationPanel;
	}
	
	private void initUI() {
		this.menuBar    = new MenuBar(this);
		this.coverPanel = new CoverPanel();
		this.navigationPanel = new NavigationPanel();
		this.navigationPanel.getBackButton().addActionListener(new BackListener());
		this.navigationPanel.getNextButton().addActionListener(new NextListener());

		this.splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, getCoverPanel(), getNavigationPanel());
		this.splitPane.setEnabled(false);
		this.splitPane.setResizeWeight(1);
		this.splitPane.setDividerSize(0);
	}

	private void layoutUI() {
		add(getSplitPane());
	}

	@Override
	public void run() {
		setTitle("MS2-Deisotoper " + VERSION);
		setJMenuBar(menuBar);
		pack();
		setLocation(getPosition().width, getPosition().height);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		toFront();
	}

	public void updatePanel() {
		repaint();
		revalidate();
		pack();
	}

	public Dimension getPosition() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize  = getPreferredSize();
		
		// Center the frame WRT user screen size
		int width  = screenSize.width / 2 - frameSize.width / 2;
		int height = screenSize.height / 3 - frameSize.height / 3;
		return new Dimension(width, height);
	}

	private class BackListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Close the program if they click the Exit button
			if (getNavigationPanel().isShowingExitButton()) {
				System.exit(0);

				// Otherwise, switch between different panels
			} else {
				getBackPanel();
			}
		}

	}

	private class NextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Run the appropriate task if they click the Run button
			if (getNavigationPanel().isShowingRunButton()) {
				int returnVal 
					= JOptionPane.showConfirmDialog(MainFrame.this, "Run MS2-Deisotoper?", 
							"Confirmation", JOptionPane.YES_NO_OPTION);
				
				if (returnVal == JOptionPane.YES_OPTION) {
					startWorker();
				}

				// Otherwise, switch between different panels
			} else {
				getNextPanel();
			}
		}
	}
	
	private void getBackPanel() {
		Component c = getSplitPane().getTopComponent();

		getSplitPane().remove(c);
		getSplitPane().add(getCoverPanel());
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		getNavigationPanel().updateBackButton();
		getNavigationPanel().updateNextButton();
	}
	
	private void getNextPanel() {
		Component c = getSplitPane().getTopComponent();

		// Switch to the next panel of the TaskPanel
		if (c instanceof CoverPanel) {
			DeisotopingPanel p = new DeisotopingPanel();
			getSplitPane().remove(c);
			getSplitPane().add(p);
			updatePanel();
			getNavigationPanel().updateBackButton();
			getNavigationPanel().updateNextButton();

		}
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
	
	public void getResultsPanel(ResultsPanel p) {
		Component c = getSplitPane().getTopComponent();
		
		getSplitPane().remove(c);
		getSplitPane().add(p);
		updatePanel();
		getNavigationPanel().updateBackButton();
		getNavigationPanel().removeNextButton();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);		
	}
	
	public void showError() {
		JOptionPane.showMessageDialog(this, "Error processing file. Terminating MS2-Deisotoper.", 
									  "Error", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}

	private void startWorker() {
		Component c = getSplitPane().getTopComponent();

		if (c instanceof DeisotopingPanel) {
			DeisotopingPanel p = (DeisotopingPanel) c;
			p.getProgressPanel().getProgressBar().setIndeterminate(true);
			p.setEnabled(false);
			getNavigationPanel().setEnabled(false);

			if (p.hasValidForm()) {
				DeisotopingWorker w = new DeisotopingWorker(MainFrame.this, p);
				w.execute();
			}
		}

	}

}
