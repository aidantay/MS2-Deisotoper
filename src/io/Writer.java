package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public interface Writer {

	public File getFile();
	public BufferedWriter getWriter();
	public void closeWriter() throws IOException;

}
