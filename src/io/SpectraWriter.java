package io;

import java.io.IOException;

import io.Writer;
import model.Spectra;

public interface SpectraWriter extends Writer {
	
	public void writeSpectra(Spectra s) throws IOException;

}
