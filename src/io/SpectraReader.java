package io;

import java.util.List;

import model.Spectra;

public interface SpectraReader extends Reader {

	public List<Spectra> getSpectraList();

}
