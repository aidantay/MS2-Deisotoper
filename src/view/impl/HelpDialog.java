package view.impl;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import controller.impl.MainFrame;

public class HelpDialog extends JDialog {

	private static final long serialVersionUID = 5020070723078914587L;

	private static final HelpDialog INSTANCE = new HelpDialog();
	
	private static final String DESCRIPTION  
		= "Quick Start Guide\n\n"
		  + "1.   Click the Add button and select one or more fragment ion spectra "
		  + "files in .MGF format.\n\n"
		  + "2.   Alter the MS2-Deisotoper input parameters to your needs. The "
		  + "default parameters will perform well for most datasets but may be "
		  + "altered. You may input multiple values; they must be comma separated.\n\n"
		  + "3.   Alter the MS2-Deisotoper output parameters to your needs. If "
		  + "toggled, MS2-Deisotoper will output diagnostic files in addition to "
		  + "your deisotoped fragment ion spectra.\n\n"
		  + "4.   Click the Run button to deisotope each fragment ion spectra file.\n\n"
		  + "5.   After deisotoping, MS2-Deisotoper will report file paths corresponding"
		  + "to where your deisotoped fragment ion spectra files are located. Fragment "
		  + "ion spectra deisotoped by MS2-Deisotoper will be suffixed with XmYi.";
	
	private JTextPane helpPane;

	private HelpDialog() {
		initUI();
		layoutUI();
	}
	
	public void run(MainFrame mainFrame) {
		pack();
		toFront();
		setLocation(mainFrame.getPosition().width, mainFrame.getPosition().height);
		setSize(mainFrame.getSize().width, mainFrame.getSize().height);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Help");
		setVisible(true);
	}
	
	public static HelpDialog getInstance() {
		return INSTANCE;
	}
	
	public JTextPane getHelpPane() {
		return helpPane;
	}

	private void initUI() {
		this.helpPane = new JTextPane();
		this.helpPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.helpPane.setEditable(false);
		this.helpPane.setDocument(getDocument());
//		this.helpPane.setEditorKit(new WrapEditorKit());
	}
	
	private void layoutUI() {
		add(getHelpPane());
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
