package io;

import java.io.File;

public interface Reader {

	public File getFile();
	public void readFile() throws Exception;
	
}
