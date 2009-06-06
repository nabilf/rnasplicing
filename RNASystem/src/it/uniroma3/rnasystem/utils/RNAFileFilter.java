package it.uniroma3.rnasystem.utils;

import java.io.File;
import java.io.FilenameFilter;

public class RNAFileFilter implements FilenameFilter {
	   
//		public boolean accept(File f) {
//	        if (f.isDirectory()) return true;
//	        String name = f.getName().toLowerCase();
//	        System.out.println("Name "+name+"ends with "+name.endsWith("rna"));
//	        return name.endsWith("rna"); 
//	    }//end accept

		public boolean accept(File dir, String name) {
			return name.endsWith("rna"); 
		}
}//end class 
