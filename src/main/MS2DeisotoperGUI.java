package main;

import javax.swing.SwingUtilities;

import controller.impl.MainFrame;

public class MS2DeisotoperGUI {

	public static void main(String[] args) throws Exception {
		MainFrame desktopApp = new MainFrame();
		SwingUtilities.invokeLater(desktopApp);
	}

}