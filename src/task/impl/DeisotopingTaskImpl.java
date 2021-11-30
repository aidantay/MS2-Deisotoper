package task.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.SpectraReader;
import io.SpectraWriter;
import io.impl.MgfReader;
import io.impl.MgfWriter;
import model.PeakList;
import model.PeakSet;
import model.Spectra;
import task.DeisotopingTask;
import task.PeakAnalyser;
import task.PeakDetector;

public class DeisotopingTaskImpl implements DeisotopingTask {

	private static final Double MAX_INTENSITY = 1000000000.0;

	private static List<Double> ppmList;
	private static List<Double> intensityList;
	private static boolean withoutIntensity;
	private static boolean removedPeaks;
	
	private String spectraFilePath;
	private SpectraReader spectraReader;

	private Map<String, SpectraWriter> outputSpectraFileMap;
	private Map<String, SpectraWriter> outputRemovedPeaksFileMap;
	
	public DeisotopingTaskImpl(String spectraFilePath, List<Double> ppmList, List<Double> intensityList, 
			boolean withoutIntensity, boolean removedPeaks) {
		
		DeisotopingTaskImpl.ppmList          = ppmList;
		DeisotopingTaskImpl.intensityList    = intensityList;
		DeisotopingTaskImpl.withoutIntensity = withoutIntensity;
		DeisotopingTaskImpl.removedPeaks     = removedPeaks;
		
		this.spectraFilePath           = spectraFilePath;
		this.outputSpectraFileMap      = new HashMap<String, SpectraWriter>();
		this.outputRemovedPeaksFileMap = new HashMap<String, SpectraWriter>();
		setSpectraReader();
	}
	
	@Override
	public List<Double> getPpmList() {
		return ppmList;
	}

	@Override
	public List<Double> getIntensityList() {
		return intensityList;
	}

	@Override
	public boolean outputWithoutIntensity() {
		return withoutIntensity;
	}
	
	@Override
	public boolean outputRemovedPeaks() {
		return removedPeaks;
	}

	public String getSpectraFilePath() {
		return spectraFilePath;
	}
	
	public SpectraReader getSpectraReader() {
		return spectraReader;
	}
	
	public Map<String, SpectraWriter> getOutputSpectraFileMap() {
		return outputSpectraFileMap;
	}
	
	public Map<String, SpectraWriter> getOutputRemovedPeaksFileMap() {
		return outputRemovedPeaksFileMap;
	}

	public void setSpectraReader() {
		if (getFileSuffix(getSpectraFilePath()).equals("mgf")) {
			this.spectraReader = new MgfReader(getSpectraFilePath());
			
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	public void run() throws Exception {
		// If we are deisotoping without any intensity consideration, then we will add an 
		// Intensity value of 1000000000%, which is (roughly) the maximum value we can have in a float.
		// The proportion of every pair of peak is likely to be less than this... 
		// If not then we got a CRAZY peak!
		if (outputWithoutIntensity()) {
			getIntensityList().add(MAX_INTENSITY);
		}
		
		getSpectraReader().readFile();
		for (Spectra s : getSpectraReader().getSpectraList()) {
			// Compare every peak to every other higher mass peak
			PeakAnalyser peakAnalyser = new PeakAnalyserImpl(s.getPeakList());
			peakAnalyser.analysePeakList();
			detectPeptidePeaks(s, peakAnalyser);
		}
		closeWriters();
	}
	
	private void detectPeptidePeaks(Spectra prevSpectra, PeakAnalyser peakAnalyser) throws Exception {
		for (double ppmValue : getPpmList()) {
			for (double intensityValue : getIntensityList()) {
				// Perform peak picking and deisotoping on each spectra
				PeakDetector peakDetector      = new PeakDetectorImpl(peakAnalyser, ppmValue, intensityValue);
				Set<PeakList> isotopicClusters = peakDetector.performPeakPicking();

				// Identify monoisotopic and remove isotopic peaks in all isotopic clusters of peptide fragments 
				PeakSet isotopicPeaksToRemove = peakDetector.getIsotopicPeaks(isotopicClusters);			
				
				// Remove isotopic peaks in all isotopic distributions 
				Spectra currSpectra1 = peakDetector.performDeisotoping(prevSpectra, isotopicPeaksToRemove);
				writeOutputSpectraFile(currSpectra1, ppmValue, intensityValue);
				
				if (outputRemovedPeaks()) {
					// Remove non-isotopic peaks in all isotopic distributions 
					Spectra currSpectra2 = peakDetector.performInverseDeisotoping(prevSpectra, isotopicPeaksToRemove);
					writeOutputRemovedPeaksFile(currSpectra2, ppmValue, intensityValue);
				}
			}
		}
	}
	
	private void writeOutputSpectraFile(Spectra s, double ppmValue, double intensityValue) throws Exception {
		String outputSpectraFilePath = getOutputSpectraFilePath(ppmValue, intensityValue);
		SpectraWriter spectraWriter  = getOutputSpectraFileMap().containsKey(outputSpectraFilePath)
									   ? getOutputSpectraFileMap().get(outputSpectraFilePath) 
									   : new MgfWriter(outputSpectraFilePath);

	    spectraWriter.writeSpectra(s);
	    getOutputSpectraFileMap().put(outputSpectraFilePath, spectraWriter);
	}
	
	private String getOutputSpectraFilePath(double ppmValue, double intensityValue) {
		String outputSpectraFileDir    = getSpectraReader().getFile().getParent();
		String outputSpectraFilePrefix = getFilePrefix(getSpectraReader().getFile().getName());
		String ppmSuffix               = Math.round(ppmValue) + "m";
		String intensitySuffix         = Math.round(intensityValue) + "i";
		
		outputSpectraFilePrefix = (intensityValue == MAX_INTENSITY) 
								  ? outputSpectraFilePrefix + "_" + ppmSuffix + "Noi"
								  : outputSpectraFilePrefix + "_" + ppmSuffix + intensitySuffix;
		
		// Assume MGF writer
		return outputSpectraFileDir + "/" + outputSpectraFilePrefix + ".mgf";
	}

	private void writeOutputRemovedPeaksFile(Spectra s, double ppmValue, double intensityValue) throws Exception {
		String outputRemovedPeaksFilePath = getOutputRemovedPeaksFilePath(ppmValue, intensityValue);
		SpectraWriter spectraWriter       = getOutputRemovedPeaksFileMap().containsKey(outputRemovedPeaksFilePath)
											? getOutputRemovedPeaksFileMap().get(outputRemovedPeaksFilePath) 
											: new MgfWriter(outputRemovedPeaksFilePath);

		spectraWriter.writeSpectra(s);
		getOutputRemovedPeaksFileMap().put(outputRemovedPeaksFilePath, spectraWriter);
	}

	private String getOutputRemovedPeaksFilePath(double ppmValue, double intensityValue) {
		String outputRemovedPeaksFileDir    = getSpectraReader().getFile().getParent();
		String outputRemovedPeaksFilePrefix = getFilePrefix(getSpectraReader().getFile().getName());
		String ppmSuffix                    = Math.round(ppmValue) + "m";
		String intensitySuffix              = Math.round(intensityValue) + "i";

		outputRemovedPeaksFilePrefix = (intensityValue == MAX_INTENSITY) 
									   ? outputRemovedPeaksFilePrefix + "_removedPeaks_" + ppmSuffix + "Noi"
									   : outputRemovedPeaksFilePrefix + "_removedPeaks_" + ppmSuffix + intensitySuffix;
		// Assume MGF writer		
		return outputRemovedPeaksFileDir + "/" + outputRemovedPeaksFilePrefix + ".mgf";
	}
			
	private void closeWriters() throws IOException {
		for (SpectraWriter w : getOutputSpectraFileMap().values()) {
			w.closeWriter();
		}
		
		if (outputRemovedPeaks()) {
			for (SpectraWriter w : getOutputRemovedPeaksFileMap().values()) {
				w.closeWriter();
			}
		}
	}
	
	private String getFilePrefix(String filePath) {
		String ext = getFileSuffix(filePath);
		return filePath.replace(".".concat(ext), "");
	}
	
	private String getFileSuffix(String filePath) {
		int extIndex = filePath.lastIndexOf(".");
		String ext  = null;

		if (extIndex > 0 && extIndex < filePath.length() - 1) {
			ext = filePath.substring(extIndex + 1).toLowerCase();
		}
		
		return ext;
	}	
	
	public String toString() {
		return getSpectraFilePath();
	}
	
}
