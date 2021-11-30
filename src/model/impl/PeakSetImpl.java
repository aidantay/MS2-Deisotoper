package model.impl;

import java.util.HashSet;

import model.PeakInfo;
import model.PeakList;
import model.PeakSet;

public class PeakSetImpl extends HashSet<PeakInfo> implements PeakSet {

	private static final long serialVersionUID = -8974051346185262928L;

	public PeakSetImpl() {
		super();
	}
	
	public PeakSetImpl(PeakList peakList) {
		super(peakList);
	}
	
	public String toString() {
		StringBuffer out = new StringBuffer();
		
		for (PeakInfo p : this) {
			out.append(p.getMass() + " " + p.getIntensity());			
			out.append(System.lineSeparator());
		}
	
		return out.toString();
	}

}
