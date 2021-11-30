package view.impl;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import io.ParameterReader;
import io.impl.ParameterTSVReader;
import view.util.MgfFileFilter;
import view.util.ParameterFileFilter;

public class DeisotopingPanel extends JPanel {

	private static final long serialVersionUID = -2547880461104225298L;
	
	private FileSelectionPanel fileSelectionPanel;
	private InputParameterPanel inputParameterPanel;
	private OutputParameterPanel outputParameterPanel;
	private ProgressPanel progressPanel;

	public DeisotopingPanel() {
		super(new GridBagLayout());

		initUI();
		layoutUI();
	}
	
	public FileSelectionPanel getFileSelectionPanel() {
		return fileSelectionPanel;
	}
	
	public InputParameterPanel getInputParameterPanel() {
		return inputParameterPanel;
	}
	
	public OutputParameterPanel getOutputParameterPanel() {
		return outputParameterPanel;
	}
	
	public ProgressPanel getProgressPanel() {
		return progressPanel;
	}

	private void initUI() {
		this.fileSelectionPanel   = new FileSelectionPanel();
		this.inputParameterPanel  = new InputParameterPanel();
		this.outputParameterPanel = new OutputParameterPanel();
		this.progressPanel        = new ProgressPanel();
	}
	
	private void layoutUI() {
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);	
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.5;
		add(getFileSelectionPanel(), c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0;
		add(getInputParameterPanel(), c);

		c.gridx = 0;
		c.gridy = 2;
		add(getOutputParameterPanel(), c);

		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0.5;
		add(getProgressPanel(), c);	
	}
	
	private class FileSelectionPanel extends JPanel {
		
		private static final long serialVersionUID = 1647495918974050166L;
		
		private JButton addSpectraFileButton;
		private JFileChooser addSpectraFileChooser;		
		private JButton removeSpectraFileButton;
		private JList<String> inputSpectraFileList;
		private DefaultListModel<String> inputSpectraFileListModel;
		
		public FileSelectionPanel() {
			super(new GridBagLayout());
			
			initUI();
			layoutUI();
		}
		
		public JButton getAddSpectraFileButton() {
			return addSpectraFileButton;
		}
		
		public JFileChooser getAddSpectraFileChooser() {
			return addSpectraFileChooser;
		}
		
		public JButton getRemoveSpectraFileButton() {
			return removeSpectraFileButton;
		}
		
		public JList<String> getInputSpectrafileList() {
			return inputSpectraFileList;
		}
		
		public DefaultListModel<String> getInputSpectraFileListModel() {
			return inputSpectraFileListModel;
		}
		
		private void initUI() {
			this.addSpectraFileButton  = new JButton("Add");
			this.addSpectraFileButton.setToolTipText("Add MS/MS MGF file/s");
			this.addSpectraFileButton.addActionListener(new AddSpectraFileListener());
			this.removeSpectraFileButton = new JButton("Remove");
			this.removeSpectraFileButton.setToolTipText("Remove MS/MS MGF file/s");
			this.removeSpectraFileButton.addActionListener(new RemoveSpectraFileListener());			
			this.addSpectraFileChooser = new JFileChooser();
			this.addSpectraFileChooser.setMultiSelectionEnabled(true);
			this.addSpectraFileChooser.setAcceptAllFileFilterUsed(false);
			this.addSpectraFileChooser.addChoosableFileFilter(new MgfFileFilter());		
			this.inputSpectraFileListModel = new DefaultListModel<String>();
			this.inputSpectraFileList      = new JList<String>();
			this.inputSpectraFileList.setModel(getInputSpectraFileListModel());
		}
		
		private void layoutUI() {
			GridBagConstraints c = new GridBagConstraints();
			
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(5, 5, 5, 5);				
			c.gridx = 0;
			c.gridy = 0;
			c.gridheight = 2;
			c.weightx = 1;
			c.weighty = 1;
			add(new JScrollPane(getInputSpectrafileList()), c);			

			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 1;
			c.gridy = 0;
			c.gridheight = 1;
			c.weightx = 0;
			c.weighty = 1;
			add(getAddSpectraFileButton(), c);
			
			c.gridx = 1;
			c.gridy = 1;
			c.weighty = 1;
			add(getRemoveSpectraFileButton(), c);			
		}
		
		private class AddSpectraFileListener implements ActionListener {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = getAddSpectraFileChooser().showOpenDialog(DeisotopingPanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					addSpectraFileToListModel(getAddSpectraFileChooser().getSelectedFiles());
				}
			}
			
		}
		
		private class RemoveSpectraFileListener implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selectedIndices = getInputSpectrafileList().getSelectedIndices();
				
				// Have to start from the highest index. So we go in reverse order
				for (int i = selectedIndices.length - 1; i >= 0; i--) {
					getInputSpectraFileListModel().remove(selectedIndices[i]);
				}

			}
		}
		
		private void addSpectraFileToListModel(File[] selectedFiles) {
			for (File f : selectedFiles) {
				if (!hasFile(f)) {
					getInputSpectraFileListModel().addElement(f.getAbsolutePath());
				}
			}
		}
		
		private boolean hasFile(File f) {
			// We dont want to let the user add in the same files...
			boolean fileExists = false;
			
			Enumeration<String> inputFileList = getInputSpectraFileListModel().elements();
			while (inputFileList.hasMoreElements()) {
				String filePath = inputFileList.nextElement();
				if (filePath.equals(f.getAbsolutePath())) {
					fileExists = true;
					break;
				}
			}
			
			return fileExists;
		}
		
	}	
	
	private class InputParameterPanel extends JPanel {

		private static final long serialVersionUID = -1688227351534919073L;

		private JButton parameterFileButton;
		private JFileChooser parameterFileChooser;
		private JLabel ppmLabel;
		private JTextField ppmField;
		private JLabel intensityLabel;
		private JTextField intensityField;
		
		public InputParameterPanel() {
			super(new GridBagLayout());

			setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Input Parameters"));
			
			initUI();
			layoutUI();
		}

		public JButton getParameterFileButton() {
			return parameterFileButton;
		}
		
		public JFileChooser getParameterFileChooser() {
			return parameterFileChooser;
		}
		
		public JLabel getPpmLabel() {
			return ppmLabel;
		}
		
		public JTextField getPpmField() {
			return ppmField;
		}
		
		public JLabel getIntensityLabel() {
			return intensityLabel;
		}
		
		public JTextField getIntensityField() {
			return intensityField;
		}

		private void initUI() {
			this.parameterFileButton = new JButton("Load from file");
			this.parameterFileButton.addActionListener(new InputParameterFileListener());

			this.parameterFileChooser = new JFileChooser();
			this.parameterFileChooser.setMultiSelectionEnabled(false);
			this.parameterFileChooser.setAcceptAllFileFilterUsed(false);
			this.parameterFileChooser.addChoosableFileFilter(new ParameterFileFilter());

			this.ppmLabel = new JLabel("Mass error (ppm):");
			this.ppmField = new JTextField(10);
			this.ppmField.setText("10");
			
			this.intensityLabel = new JLabel("Intensity cut-off (%):");
			this.intensityField = new JTextField(10);
			this.intensityField.setText("100");
		}

		private void layoutUI() {
			GridBagConstraints c = new GridBagConstraints();

	        c.fill = GridBagConstraints.HORIZONTAL;
	        c.insets = new Insets(5, 20, 5, 5);	
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 0;
			add(getParameterFileButton(), c);			

	        c.insets = new Insets(5, 20, 5, 5);	
			c.gridx = 0;
			c.gridy = 1;
			add(getPpmLabel(), c);

	        c.insets = new Insets(5, 5, 5, 5);	
			c.gridx = 1;
			c.gridy = 1;
			c.weightx = 1;
			add(getPpmField(), c);

	        c.insets = new Insets(5, 20, 5, 5);
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 0;
			add(getIntensityLabel(), c);

	        c.insets = new Insets(5, 5, 5, 5);
			c.gridx = 1;
			c.gridy = 2;
			c.weightx = 1;
			add(getIntensityField(), c);
		}
		
		private class InputParameterFileListener implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = getParameterFileChooser().showOpenDialog(DeisotopingPanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String filePath = getParameterFileChooser().getSelectedFile().getAbsolutePath();

					try {
						ParameterReader reader = new ParameterTSVReader(filePath);
						reader.readFile();

						getPpmField().setText(reader.getParameterPpmField());
						getIntensityField().setText(reader.getParameterIntensityField());
						
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(DeisotopingPanel.this, "Unable to read file.", "Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
					
				}
			}
		
		}
		
		public boolean hasValidInputParameters() {
			if (isParameterFieldValid(getPpmField().getText()) && isParameterFieldValid(getIntensityField().getText())) {
				return true;
			}			
			return false;			
		}
				
		private boolean isParameterFieldValid(String parameterField) {
			for (String parameter: parameterField.split(",")) {
				if (!isParameterValid(parameter)) {
					return false;
				}
			}
			return true;
		}
		
		private boolean isParameterValid(String parameter) {
			if (!StringUtils.isNumeric(parameter)) {
				// There's a few things about this check that are not quite right... See documentation if we need to change this
				JOptionPane.showMessageDialog(DeisotopingPanel.this, "Invalid parameter detected", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;
		}
		
	}

	private class OutputParameterPanel extends JPanel {

		private static final long serialVersionUID = -7234062840298019654L;

		private JLabel withoutIntensityLabel;
		private JCheckBox withoutIntensityFlag;
		private JLabel removedPeaksLabel;
		private JCheckBox removedPeaksFlag;
		
		public OutputParameterPanel() {
			super (new GridBagLayout());
			
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Output Parameters"));

			initUI();
			layoutUI();
		}
		
		public JLabel getWithoutIntensityLabel() {
			return withoutIntensityLabel;
		}
		
		public JCheckBox getWithoutIntensityFlag() {
			return withoutIntensityFlag;
		}

		public JLabel getRemovedPeaksLabel() {
			return removedPeaksLabel;
		}
		
		public JCheckBox getRemovedPeaksFlag() {
			return removedPeaksFlag;
		}

		private void initUI() {
			this.withoutIntensityLabel = new JLabel("Output without intensity:");
			this.withoutIntensityFlag  = new JCheckBox(); 
			this.removedPeaksLabel = new JLabel("Output removed peaks:");
			this.removedPeaksFlag  = new JCheckBox();
		}
		
		private void layoutUI() {
			GridBagConstraints c = new GridBagConstraints();
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(5, 20, 5, 5);			
			c.gridx = 0;
			c.gridy = 0;
			add(getWithoutIntensityLabel(), c);
			
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.LINE_START;
			c.insets = new Insets(5, 5, 5, 5);
			c.gridx = 1;
			c.gridy = 0;
			c.weightx = 1;
			add(getWithoutIntensityFlag(), c);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(5, 20, 5, 5);
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 0;
			add(getRemovedPeaksLabel(), c);
			
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.LINE_START;
			c.insets = new Insets(5, 5, 5, 5);
			c.gridx = 1;
			c.gridy = 1;
			c.weightx = 1;
			add(getRemovedPeaksFlag(), c);
		}
		
	}

	public void setEnabled(boolean isEnabled) {
		setEnabled(getFileSelectionPanel(), isEnabled);
		setEnabled(getInputParameterPanel(), isEnabled);
		setEnabled(getOutputParameterPanel(), isEnabled);
		setEnabled(getProgressPanel(), isEnabled);
	}
	
	private void setEnabled(Container parent, boolean isEnabled) {
		for (Component c : parent.getComponents()) {
			c.setEnabled(isEnabled);
		}		
	}
	
	public boolean hasValidForm() {
		if (getInputParameterPanel().hasValidInputParameters()) {
			return true;
		}
		return false;
	}

	public List<String> getSpectraFilePathList() {
		Enumeration<String> inputSpectraFilePaths = getFileSelectionPanel().getInputSpectraFileListModel().elements();
		List<String> spectraFilePathList          = new ArrayList<String>();
		
		while (inputSpectraFilePaths.hasMoreElements()) {
			String spectraFilePath = inputSpectraFilePaths.nextElement();
			spectraFilePathList.add(spectraFilePath);
		}

		return spectraFilePathList;
	}
	
	public List<Double> getPpmList() {
		String parameterField = getInputParameterPanel().getPpmField().getText();
		return getParameterList(parameterField);
	}
	
	public List<Double> getIntensityList() {
		String parameterField = getInputParameterPanel().getIntensityField().getText();
		return getParameterList(parameterField);
	}
	
	public boolean getWithoutIntensityFlag() {
		return getOutputParameterPanel().getWithoutIntensityFlag().isSelected();
	}
	
	public boolean getRemovedPeaksFlag() {
		return getOutputParameterPanel().getRemovedPeaksFlag().isSelected();
	}
	
	private List<Double> getParameterList(String parameterField) {
		List<Double> parameterList = new ArrayList<Double>();
		
		for (String parameter : parameterField.split(",")) {
			double parameterValue = Double.valueOf(parameter);
			parameterList.add(parameterValue);
		}
		
		return parameterList;
	}

}
