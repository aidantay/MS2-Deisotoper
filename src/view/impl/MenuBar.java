package view.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.impl.MainFrame;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = -8973697838210721528L;
	
	private static final String MENU = "MS2-Deisotoper";
	private static final String HELP = "Help";
	private static final String QUIT = "Quit";
	
	private MainFrame mainFrame;
	
	private JMenu menu;
	private JMenuItem help;
	private JMenuItem exit;
	
	public MenuBar(MainFrame mainFrame) {
		super();
		
		this.mainFrame  = mainFrame;

		initUI();
		layoutUI();
	}
	
	public MainFrame getMainFrame() {
		return mainFrame;
	}
	
	public JMenu getMenu() {
		return menu;
	}
	
	public JMenuItem getHelpItem() {
		return help;
	}
	
	public JMenuItem getExitItem() {
		return exit;
	}
	
	private void initUI() {
		this.menu = new JMenu(MENU);
		this.help = new JMenuItem(HELP);
		this.help.addActionListener(new ShowHelpListener());
		this.exit = new JMenuItem(QUIT);
		this.exit.addActionListener(new ExitAppListener());
	}

	private void layoutUI() {
		getMenu().add(getHelpItem());
		getMenu().addSeparator();
		getMenu().add(getExitItem());
		add(getMenu());
	}
	
	private class ShowHelpListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			HelpDialog helpDialog = HelpDialog.getInstance();
			if (helpDialog.isVisible()) {
				helpDialog.toFront();
				
			} else {
				helpDialog.run(getMainFrame());
			}
		}

	}

	private class ExitAppListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			getMainFrame().setVisible(false);
			getMainFrame().dispose();
			System.exit(0);
		}
		
	}

}
