package task;

import model.PeakList;
import model.impl.PeakComparisonTable;

public interface PeakAnalyser {
	
	public PeakList getPeakList();
	public PeakComparisonTable getNeutronTable();
	public PeakComparisonTable getHalfNeutronTable();
	public PeakComparisonTable getIntensityTable();	
	public void analysePeakList();

}