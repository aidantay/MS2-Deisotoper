package model.impl;

import java.util.HashMap;
import java.util.Map;

public class PeakComparisonTable {
	
	private Map<Integer, Map<Integer, Double>> table;
	
	public PeakComparisonTable() {
		this.table = new HashMap<Integer, Map<Integer, Double>>();
	}
	
	public Map<Integer, Map<Integer, Double>> getTable() {
		return table;
	}
	
	public void addValue(int i, int j, Double value) {
		Map<Integer, Double> row = (getTable().containsKey(i)) ? getTable().get(i) : new HashMap<Integer, Double>();
		row.put(j, value);
		getTable().put(i, row);
	}
	
	public Double getValue(int i, int j) {
		Map<Integer, Double> row = (getTable().containsKey(i)) ? getTable().get(i) : new HashMap<Integer, Double>();
		return row.get(j);
	}
	
	public int getMaxRowSize() {
		return getTable().size();
	}
	
	public String toString() {
		StringBuffer out = new StringBuffer();
		
		out.append("" + "\t");
		for (int i = 0; i < getTable().size() + 1; i++) {
			out.append(i + "\t");					
		}
		out.append(System.getProperty("line.separator"));

		for (int i = 0; i < getTable().size(); i++) {
			Map<Integer, Double> row = getTable().get(i);	
			out.append(i + "\t");
			for (int j = 0; j < getTable().size() + 1; j++) {
				out.append(row.get(j) + "\t");
			}
			out.append(System.getProperty("line.separator"));			
			
		}
		return out.toString();
	}

	
}
