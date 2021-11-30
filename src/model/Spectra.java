package model;

public interface Spectra {

	public String getTitle();
	public String getScans();
	public String getRetentionTime();
	public String getCharge();
	public String getPepMass();
	public PeakList getPeakList();

}