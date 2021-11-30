package task;

import java.util.List;
import java.util.Map;

import io.SpectraReader;
import io.SpectraWriter;

public interface DeisotopingTask extends Task {

	public List<Double> getPpmList();
	public List<Double> getIntensityList();
	public boolean outputWithoutIntensity();
	public boolean outputRemovedPeaks();
	
	public String getSpectraFilePath();
	public SpectraReader getSpectraReader();
	public Map<String, SpectraWriter> getOutputRemovedPeaksFileMap();
	public void setSpectraReader();
	public void run() throws Exception;	

}
