package view.impl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class CoverPanel extends JPanel {

	private static final long serialVersionUID = 8295130068089023334L;

	private static final String DESCRIPTION
		= "MS2-Deisotoper is a tool solely for peak picking and deisotoping "
		  + "of centroided fragment ion spectra. It identifies monoisotopic "
		  + "peaks and removes isotopic peaks from isotopic clusters of "
		  + "peptide fragments."
		  + "\n\n"
		  + "For more information, please refer to "
		  + "Menu -> Help"
		  + "\n\n"
		  + "Press 'Next' to continue.";

	private JTextPane description;

	public CoverPanel() {
		super(new GridBagLayout());
		
		initUI();
		layoutUI();
	}

	public JTextPane getDescription() {
		return description;
	}

	private void initUI() {
		this.description = new JTextPane();
		this.description.setEditable(false);
		this.description.setDocument(getDocument());
	}
	
	private void layoutUI() {
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		add(getDescription(), c);
	}
	
	private DefaultStyledDocument getDocument() {
		DefaultStyledDocument doc = new DefaultStyledDocument();
		SimpleAttributeSet attrs  = new SimpleAttributeSet();
		StyleConstants.setFontSize(attrs, 12);
		StyleConstants.setBold(attrs, true);
		
		try {
			doc.insertString(0, DESCRIPTION, attrs);
			
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return doc;
	}

}
