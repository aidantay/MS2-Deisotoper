package view.impl;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

public class NavigationPanel extends JPanel {

	private static final long serialVersionUID = 8295130068089023334L;

	private static final String EXIT = "Exit";
	private static final String NEXT = "Next";
	private static final String BACK = "Back";
	private static final String RUN  = "Run";
	
	private JButton backButton;
	private JButton nextButton;
	
	public NavigationPanel() {
		super(new GridBagLayout());
		
		initUI();
		layoutUI();
	}
	
	public JButton getBackButton() {
		return backButton;
	}
	
	public JButton getNextButton() {
		return nextButton;
	}
	
	private void initUI() {
		this.backButton = new JButton(EXIT);
		this.nextButton = new JButton(NEXT);
	}
	
	private void layoutUI() {
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.insets = new Insets(5, 5, 5, 5);
		add(getBackButton(), c);
	
		c.gridx = 1;
		c.gridy = 0;
		add(getNextButton(), c);
	}
	
	public void setEnabled(boolean isEnabled) {
		for (Component c : getComponents()) {
			c.setEnabled(isEnabled);
		}		
	}
	
	public boolean isShowingExitButton() {
		return getBackButton().getText().equals(EXIT);
	}
	
	public boolean isShowingRunButton() {
		return getNextButton().getText().equals(RUN);
	}
	
	public void updateBackButton() {
		if (isShowingExitButton()) {
			getBackButton().setText(BACK);
		} else {
			getBackButton().setText(EXIT);
		}
	}

	public void updateNextButton() {
		if (isShowingRunButton()) {
			getNextButton().setText(NEXT);
		} else {
			getNextButton().setText(RUN);
		}
	}
	
	public void removeNextButton() {
		remove(getNextButton());
	}

}
