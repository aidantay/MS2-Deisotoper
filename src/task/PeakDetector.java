package task;

import java.util.Set;

import model.PeakList;
import model.PeakSet;
import model.Spectra;
import model.impl.PeakComparisonTable;

public interface PeakDetector {
	
	public PeakAnalyser getPeakAnalyser();
	public double getPpmValue();
	public double getIntensityValue();
	public Set<PeakList> performPeakPicking();
	public PeakSet getIsotopicPeaks(Set<PeakList> isotopicClusters);
	public Set<PeakList> getIsotopicClusters(PeakComparisonTable massTable);
	public Spectra performDeisotoping(Spectra prevSpectra, PeakSet isotopicPeaksToRemove);
	public Spectra performInverseDeisotoping(Spectra prevSpectra, PeakSet isotopicPeaksToRemove);

}