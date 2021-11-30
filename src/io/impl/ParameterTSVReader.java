package io.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import io.ParameterReader;

public class ParameterTSVReader implements ParameterReader {

	private File file;
	private String parameterPpmField;
	private String parameterIntensityField;

	public ParameterTSVReader(String filePath) {
		this.file = new File(filePath);
	}
	
	@Override
	public File getFile() {
		return file;
	}
	
	@Override
	public String getParameterPpmField() {
		return parameterPpmField;
	}
	
	@Override
	public void setParameterPpmField(String parameterPpmField) {
		this.parameterPpmField = parameterPpmField;
	}
	
	@Override
	public String getParameterIntensityField() {
		return parameterIntensityField;
	}	
	
	@Override
	public void setParameterIntensityField(String parameterIntensityField) {
		this.parameterIntensityField = parameterIntensityField;
	}

	@Override
	public void readFile() throws Exception {
		String line;
		
		BufferedReader reader = new BufferedReader(new FileReader(getFile()));
        while ((line = reader.readLine()) != null) {
        	// First line is formatted as such: Mass error\t1,2,etc...
        	// Second line is formatted as such: Intensity\t1,2,etc...
        	String[] lineValues = line.split("\\t");
        	
        	if (lineValues[0].equals("Mass error")) {
        		setParameterPpmField(lineValues[1]);
        		
        	} else if (lineValues[0].equals("Intensity")) {
        		setParameterIntensityField(lineValues[1]);
        	}

        }
        reader.close();

	}

}
