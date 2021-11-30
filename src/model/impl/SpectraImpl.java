package model.impl;

import model.PeakList;
import model.Spectra;

public class SpectraImpl implements Spectra {

	private String title;
	private String scans;
	private String retentionTime;
	private String charge;
	private String pepMass;
	private PeakList peakList;
			
	public SpectraImpl(String title, String scans, String retentionTime, 
					   String charge, String pepMass, PeakList peakList) {

		this.title         = title;
		this.scans         = scans;
		this.retentionTime = retentionTime;
		this.charge        = charge;
		this.pepMass       = pepMass;
		this.peakList      = peakList;
	}
	
	public SpectraImpl(Spectra s) {
		this(s.getTitle(), s.getScans(), s.getRetentionTime(), 
			 s.getCharge(), s.getPepMass(), new PeakListImpl(s.getPeakList()));		
	}
	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getScans() {
		return scans;
	}

	@Override
	public String getRetentionTime() {
		return retentionTime;
	}

	@Override
	public String getCharge() {
		return charge;
	}

	@Override
	public String getPepMass() {
		return pepMass;
	}
	
	@Override
	public PeakList getPeakList() {
		return peakList;
	}

	@Override
	public String toString() {
		StringBuffer out = new StringBuffer();
		out.append("TITLE=" + getTitle());
		out.append(System.getProperty("line.separator"));
		out.append("SCANS=" + getScans());
		out.append(System.getProperty("line.separator"));
		out.append("RTINSECONDS=" + getRetentionTime());
		out.append(System.getProperty("line.separator"));
		out.append("CHARGE=" + getCharge());
		out.append(System.getProperty("line.separator"));
		out.append("PEPMASS=" + getPepMass());
		out.append(System.getProperty("line.separator"));
		out.append(getPeakList());
		return out.toString();
	}
	
}
