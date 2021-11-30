package io;

import io.Reader;

public interface ParameterReader extends Reader {

	public String getParameterPpmField();
	public void setParameterPpmField(String parameterPpmField);
	
	public String getParameterIntensityField();
	public void setParameterIntensityField(String parameterIntensityField);
}
