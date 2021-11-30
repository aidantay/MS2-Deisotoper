package task.impl;

import java.util.HashSet;
import java.util.Set;

import model.PeakInfo;
import model.PeakList;
import model.PeakSet;
import model.Spectra;
import model.impl.PeakComparisonTable;
import model.impl.PeakListImpl;
import model.impl.PeakSetImpl;
import model.impl.SpectraImpl;
import task.PeakAnalyser;
import task.PeakDetector;

public class PeakDetectorImpl implements PeakDetector {

	private PeakAnalyser peakAnalyser;
	private double ppmValue;
	private double intensityValue;
	
	public PeakDetectorImpl(PeakAnalyser peakAnalyser, double ppmValue, double intensityValue) {
		this.peakAnalyser   = peakAnalyser;
		this.ppmValue 	    = ppmValue;
		this.intensityValue = intensityValue;		
	}
	
	@Override
	public PeakAnalyser getPeakAnalyser() {
		return peakAnalyser;
	}
	
	@Override
	public double getPpmValue() {
		return ppmValue;
	}
	
	@Override
	public double getIntensityValue() {
		return intensityValue;
	}
	
	public Set<PeakList> performPeakPicking() {
		Set<PeakList> neutronIsotopicClusters     = getIsotopicClusters(getPeakAnalyser().getNeutronTable());
		Set<PeakList> halfNeutronIsotopicClusters = getIsotopicClusters(getPeakAnalyser().getHalfNeutronTable());
		
		Set<PeakList> isotopicDistributions = new HashSet<PeakList>();
		isotopicDistributions.addAll(neutronIsotopicClusters);
		isotopicDistributions.addAll(halfNeutronIsotopicClusters);
		return isotopicDistributions;
	}

	public Set<PeakList> getIsotopicClusters(PeakComparisonTable massTable) {
		Set<PeakList> getIsotopicClusters = new HashSet<PeakList>();
		int numPeaks = getPeakAnalyser().getPeakList().size();
		
		for (int i = 0; i < numPeaks; i++) {
			for (int j = i + 1; j < numPeaks; j++) {
				// For find isotopic peaks in the table
				// TODO We can probably do some optimisation to make it run faster.
				// Mainly because there is no need to compare peaks after a certain point (But at what point?)
				PeakList isotopicPeaks = getIsotopicPeaks(massTable, i, j);
				
				if (isotopicPeaks.size() != 0) {
					PeakInfo peakPairA = getPeakAnalyser().getPeakList().get(i);
					isotopicPeaks.add(0, peakPairA);
					getIsotopicClusters.add(isotopicPeaks);
				}
			}
		}
		return getIsotopicClusters;
	}
	
	// A peak (B) is an isotopic peak of another (A) 
	// IF they are within a mass shift range
	// AND the intensity of peak B is less than a given proportion of peak A  
	private PeakList getIsotopicPeaks(PeakComparisonTable massTable, int peakIndexA, int peakIndexB) {
		double ppm       = massTable.getValue(peakIndexA, peakIndexB);
		double intensity = getPeakAnalyser().getIntensityTable().getValue(peakIndexA, peakIndexB);
		// If we want to include a dynamic intensity threshold, then we need to modify somewhere here.

		// Not sure what the equalities should be. >= or >. Does it matter? Probably. A lot? Probably not.
		if (ppm >= getPpmValue()
			|| intensity > getIntensityValue()
			|| peakIndexA + 1 == getPeakAnalyser().getPeakList().size() 
			|| peakIndexB + 1 == getPeakAnalyser().getPeakList().size()) {
			
			return new PeakListImpl();
			
		} else {
			PeakList isotopicPeaks = getIsotopicPeaks(massTable, peakIndexA + 1, peakIndexB + 1);
			
			PeakInfo peakPairB = getPeakAnalyser().getPeakList().get(peakIndexB);
			isotopicPeaks.add(peakPairB);
			return isotopicPeaks;
		}
	}
	
	public PeakSet getIsotopicPeaks(Set<PeakList> isotopicClusters) {
		PeakSet isotopicPeakSet = new PeakSetImpl();
		
		for (PeakList peakList : isotopicClusters) {
			// Every peak after the first one is an isotopic peak that we want to remove
			for (int i = 1; i < peakList.size(); i++) {
				isotopicPeakSet.add(peakList.get(i));
			}
		}
		return isotopicPeakSet;
	}

	public Spectra performDeisotoping(Spectra prevSpectra, PeakSet isotopicPeaksToRemove) {
		Spectra currSpectra = new SpectraImpl(prevSpectra);
		currSpectra.getPeakList().removeAll(isotopicPeaksToRemove);
		return currSpectra;
	}
	
	public Spectra performInverseDeisotoping(Spectra prevSpectra, PeakSet isotopicPeaksToRemove) {
		Spectra currSpectra = new SpectraImpl(prevSpectra);
		currSpectra.getPeakList().retainAll(isotopicPeaksToRemove);
		return currSpectra;
	}
	
}
