package io.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.SpectraReader;
import model.PeakInfo;
import model.PeakList;
import model.Spectra;
import model.impl.PeakInfoImpl;
import model.impl.PeakListImpl;
import model.impl.SpectraImpl;

public class MgfReader implements SpectraReader {

	private static final String TITLE          = "TITLE";
	private static final String SCANS          = "SCANS";
	private static final String RETENTION_TIME = "RTINSECONDS";
	private static final String CHARGE         = "CHARGE";
	private static final String PEPMASS        = "PEPMASS";
		
	private File file;
	private List<Spectra> spectraList;

	public MgfReader(String filePath) {
		this.file = new File(filePath);
		this.spectraList = new ArrayList<Spectra>();
	}
	
	@Override
	public File getFile() {
		return file;
	}
	
	@Override
	public List<Spectra> getSpectraList() {
		return spectraList;
	}

	@Override
	public void readFile() throws NumberFormatException, IOException {
		String line;
		
		BufferedReader reader = new BufferedReader(new FileReader(getFile()));        	
        while ((line = reader.readLine()) != null) {
        	// Ignore the header lines
        	if (line.startsWith("#")) {
        		continue;
        		
        	} else {
            	if (line.startsWith("BEGIN")) {
        			Spectra spectra = createSpectra(reader);
        			getSpectraList().add(spectra);
        		}
        	}
        }
        reader.close();
	}
		
	private Spectra createSpectra(BufferedReader reader) throws NumberFormatException, IOException {
		String line;
		String title         = new String();
		String scans         = new String();
		String retentionTime = new String();
		String charge        = new String();
		String pepMass       = new String();
		PeakList peakList    = new PeakListImpl();
		
		while (!(line = reader.readLine()).startsWith("END")) {
			if (line.startsWith(TITLE)) {
				title = line.split("=")[1];
				
			} else if (line.startsWith(SCANS)) {
				scans = line.split("=")[1];
				
			} else if (line.startsWith(RETENTION_TIME)) {
				retentionTime = line.split("=")[1];
				
			} else if (line.startsWith(CHARGE)) {
				charge = line.split("=")[1];
				
			} else if (line.startsWith(PEPMASS)) {
				pepMass = line.split("=")[1];
				
			} else {
				String[] peakInfo = line.split("\\s");
				
				PeakInfo peakPair = new PeakInfoImpl(Double.valueOf(peakInfo[0]), Double.valueOf(peakInfo[1]));
				peakList.add(peakPair);
			}
		}
        
        return new SpectraImpl(title, scans, retentionTime, charge, pepMass, peakList);
	}
	
}
