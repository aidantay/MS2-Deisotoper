package view.impl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ProgressPanel extends JPanel {

	private static final long serialVersionUID = -6115517017676317328L;

	private JTextArea progressArea;
	private JProgressBar progressBar;

	public ProgressPanel() {
		super(new GridBagLayout());
		
		initUI();
		layoutUI();
	}
	
	public JTextArea getProgressArea() {
		return progressArea;
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	private void initUI() {
		this.progressArea  = new JTextArea();
		this.progressBar   = new JProgressBar();
	}
	
	private void layoutUI() {
		GridBagConstraints c = new GridBagConstraints();
	
	    c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(5, 5, 5, 5);	
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		add(new JScrollPane(progressArea), c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0;
		add(progressBar, c);
	}

}
