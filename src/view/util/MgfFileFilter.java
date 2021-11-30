package view.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class MgfFileFilter extends FileFilter {
	
	public MgfFileFilter() {
		super();		
	}
		
	@Override
	// We put a filter for .mgf files
	public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }				
		
		String ext = null;
		int i = f.getName().lastIndexOf(".");
		if (i > 0 && i < f.getName().length() - 1) {
			ext = f.getName().substring(i + 1).toLowerCase();
		}
			
		if (ext != null) {
			if (ext.equals("mgf")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "Mascot Generic (.mgf)";
	}	
	
}
