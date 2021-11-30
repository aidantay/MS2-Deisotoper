package task.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import controller.impl.MainFrame;
import task.DeisotopingTask;
import task.impl.DeisotopingTaskImpl;
import view.impl.DeisotopingPanel;
import view.impl.ResultsPanel;

public class DeisotopingWorker extends SwingWorker<Set<String>, String> {

	private MainFrame mainFrame;
	private DeisotopingPanel deisotopingPanel;

	public DeisotopingWorker(MainFrame mainFrame, DeisotopingPanel deisotopingPanel) {
		this.mainFrame        = mainFrame;
		this.deisotopingPanel = deisotopingPanel;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public DeisotopingPanel getDeisotopingPanel() {
		return deisotopingPanel;
	}

	@Override
	protected void process(List<String> chunks) {
		String message = "Processing..... " + chunks.get(chunks.size() - 1) + "\n";
		getDeisotopingPanel().getProgressPanel().getProgressArea().append(message);
		getMainFrame().updatePanel();
	}

	@Override
	protected Set<String> doInBackground() throws Exception {		
		Set<String> outputDirs = new HashSet<String>();
		for (String spectraFilePath : getDeisotopingPanel().getSpectraFilePathList()) {
			DeisotopingTask task
				= new DeisotopingTaskImpl(spectraFilePath, getDeisotopingPanel().getPpmList(), 
						getDeisotopingPanel().getIntensityList(), getDeisotopingPanel().getWithoutIntensityFlag(), 
						getDeisotopingPanel().getRemovedPeaksFlag());
			
			publish(task.getSpectraFilePath());
			task.run();

			outputDirs.add(task.getSpectraReader().getFile().getParent());
		}
		return outputDirs;
	}

	@Override
	protected void done() {
		try {
			Set<String> outputDirSet = get();
			ResultsPanel p = new ResultsPanel(outputDirSet);
			getMainFrame().getResultsPanel(p);
			getMainFrame().getNavigationPanel().setEnabled(true);
			
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			getMainFrame().showError();
		}

	}

}
