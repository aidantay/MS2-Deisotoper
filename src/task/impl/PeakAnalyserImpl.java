package task.impl;

import model.PeakInfo;
import model.PeakList;
import model.impl.PeakComparisonTable;
import task.PeakAnalyser;

public class PeakAnalyserImpl implements PeakAnalyser {

	private static final double NEUTRON_MASS 	  = (double) 1.00335;	// Singly charged
	private static final double HALF_NEUTRON_MASS = NEUTRON_MASS / 2;	// Doubly charged

	private PeakList peakList;
	private PeakComparisonTable neutronTable;
	private PeakComparisonTable halfNeutronTable;
	private PeakComparisonTable intensityTable;
	
	public PeakAnalyserImpl(PeakList peakList) {
		this.peakList         = peakList;
		this.neutronTable     = new PeakComparisonTable();
		this.halfNeutronTable = new PeakComparisonTable();
		this.intensityTable   = new PeakComparisonTable();		
	}
	
	@Override
	public PeakList getPeakList() {
		return peakList;
	}

	@Override
	public PeakComparisonTable getNeutronTable() {
		return neutronTable;
	}
	
	@Override
	public PeakComparisonTable getHalfNeutronTable() {
		return halfNeutronTable;
	}
	
	@Override
	public PeakComparisonTable getIntensityTable() {
		return intensityTable;
	}
	
	@Override
	public void analysePeakList() {
		int numPeaks = getPeakList().size();
		
		for (int i = 0; i < numPeaks; i++) {
			for (int j = i + 1; j < numPeaks; j++) {
				// int i = index of first peak (A)
				// int j = index of peak/s after A (B)
				PeakInfo peakPairA = getPeakList().get(i);
				PeakInfo peakPairB = getPeakList().get(j);
				
				double intensityProportion  = getIntensityProportion(peakPairA, peakPairB);
				double neutronMassShift     = getMassShift(peakPairA, peakPairB, NEUTRON_MASS);
				double halfNeutronMassShift = getMassShift(peakPairA, peakPairB, HALF_NEUTRON_MASS);
								
				getNeutronTable().addValue(i, j, neutronMassShift);
				getHalfNeutronTable().addValue(i, j, halfNeutronMassShift);
				getIntensityTable().addValue(i, j, intensityProportion);

//				System.out.println(peakPairA.getMass() + "\t" + peakPairA.getIntensity() + "\t" + 
//								   peakPairB.getMass() + "\t" + peakPairB.getIntensity() + "\t");					
			}
		}
	}
	
	private double getMassShift(PeakInfo peakPairA, PeakInfo peakPairB, double neutronMass) {
		// PPM = [(massA + neutronMass - massB) / (massA + neutronMass)] * (float) Math.pow(10, 6)
		double massA  = peakPairA.getMass();
		double massB  = peakPairB.getMass();
		double top    = (massA + neutronMass  - massB);
		double bottom = (massA + neutronMass);
		double ppm    = (top / bottom) * (double) Math.pow(10, 6);
		return Math.abs(ppm);
	}
	
	private double getIntensityProportion(PeakInfo peakPairA, PeakInfo peakPairB) {	
		// Calculate the proportion of intensityB against intensityA 
		double intensityA = peakPairA.getIntensity();
		double intensityB = peakPairB.getIntensity();
		double proportion = (intensityB / intensityA) * 100;
		return proportion;
	}

}
