package io.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import io.SpectraWriter;
import model.Spectra;

public class MgfWriter implements SpectraWriter {

	private BufferedWriter writer;			
	private File file;

	public MgfWriter(String filePath) throws IOException {
		this.file = new File(filePath);
		this.writer = new BufferedWriter(new FileWriter(getFile()));
		getWriter().write(getMgfHeaderLines());
	}

	@Override
	public File getFile() {
		return file;
	}
	
	@Override
	public BufferedWriter getWriter() {
		return writer;
	}
	
	@Override
	public void closeWriter() throws IOException {
		getWriter().close();
	}

	@Override
	public void writeSpectra(Spectra s) throws IOException {
		getWriter().write("BEGIN IONS\n");
		getWriter().write(s.toString());
		getWriter().write("END IONS\n\n");
	}
	
	private String getMgfHeaderLines() {
		return  "#   MGF file created by MassMatrix MS data conversion tools for database search \n" + 
				"#   Format of MGF files:\n" +
				"#[Comments]\n" +
				"#[Query 1]\n" +
				"#[Query 2]\n" +
				"#...\n" +
				"#[Query N]\n" +
				"#\n" +
				"#   Format of each query:\n" +
				"#BEGIN IONS\n" +
				"#TITLE=[title of the entry]\n" +
				"#SCANS=[scan#]\n" +
				"#RTINSECONDS=[retention time in seconds]\n" +
				"#CHARGE=[charge state of the spectrum]\n" +
				"#PEPMASS=[precursor m/z value of the spectrum]\n" +
				"#[m/z intensity of product ion 1]\n" +
				"#[m/z intensity oListf product ion 2]\n" +
				"#...\n" +
				"#[m/z intensity of product ion m]\n" +
				"#END IONS\n" +
				"#\n" +
				"########################################\n" +
				"#    For more info,                    #\n" +
				"#    please contact hxx58@case.edu     #\n" +
				"#    or vist http://www.massmatrix.net #\n" +
				"########################################\n\n";
	}
	
}
