package model.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;

import model.PeakInfo;
import model.PeakList;

public class PeakListImpl extends ArrayList<PeakInfo> implements PeakList {

	private static final long serialVersionUID = 4350437988636932863L;

	private static DecimalFormat DF;
	
	public PeakListImpl() {
		super();
		
		if (DF == null) {
			 DF = new DecimalFormat("#");
			 DF.setMaximumFractionDigits(100);
		}
	}
	
	public PeakListImpl(PeakList peakList) {
		super(peakList);
	}

	
	public String printToListFormat() {
		StringBuffer out = new StringBuffer();
		
		out.append("[");
		for (PeakInfo p : this) {
			out.append(DF.format(p.getMass()) + " " + DF.format(p.getIntensity()));
			if (indexOf(p) != size() - 1) {
				out.append(", ");				
			}
		}
		out.append("]");
		return out.toString();
	}
	
	public String toString() {
		StringBuffer out = new StringBuffer();
		
		for (PeakInfo p : this) {
			out.append(DF.format(p.getMass()) + " " + DF.format(p.getIntensity()));
			out.append(System.lineSeparator());
		}
	
		return out.toString();
	}

}
