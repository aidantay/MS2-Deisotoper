package view.impl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class ResultsPanel extends JPanel {

	private static final long serialVersionUID = -6115517017676317328L;

	private static final String INFO = "Output files are placed in the following directories.";
	private Set<String> outputDirSet;

	private JTextPane resultsInfo;
	private JList<String> resultsList;
	private DefaultListModel<String> resultsListModel;
	
	public ResultsPanel(Set<String> outputDirSet) {
		super(new GridBagLayout());

		this.outputDirSet = outputDirSet;
		
		initUI();
		layoutUI();
	}
	
	public Set<String> getOutputDirs() {
		return outputDirSet;
	}
	
	public JTextPane getResultsInfo() {
		return resultsInfo;
	}
	
	public JList<String> getResultsList() {
		return resultsList;
	}
	
	public DefaultListModel<String> getResultsListModel() {
		return resultsListModel;
	}
	
	private void initUI() {
		this.resultsInfo = new JTextPane();
		this.resultsInfo.setEditable(false);
		this.resultsInfo.setDocument(getDocument());

		this.resultsListModel = new DefaultListModel<String>();
		this.resultsList = new JList<String>();
		this.resultsList.setModel(getResultsListModel());
		addResults();
	}
	
	private void layoutUI() {
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);	
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		add(getResultsInfo(), c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 1;
		add(new JScrollPane(getResultsList()), c);
	}
	
	private void addResults() {
		for (String s : getOutputDirs()) {
			getResultsListModel().addElement(s);
		}
	}

	private DefaultStyledDocument getDocument() {
		DefaultStyledDocument doc = new DefaultStyledDocument();
		SimpleAttributeSet attrs  = new SimpleAttributeSet();
		StyleConstants.setFontSize(attrs, 12);
		StyleConstants.setBold(attrs, true);
		
		try {
			doc.insertString(0, INFO, attrs);
			
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
}
