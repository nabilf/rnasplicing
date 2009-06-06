package it.uniroma3.rnafolding.algorithm;

import it.uniroma3.rnafolding.exception.RNAFoldingException;


public class ExtendedDB {

	public static String execute(String dBnotation) throws RNAFoldingException {
		String extended = ""; 
		
		char c = dBnotation.charAt(0) ;
		int count = 1; 
		
		for(int i = 1; i<dBnotation.length(); i++){
			char a = dBnotation.charAt(i); 
			if(c != a){
				extended += ""+c+count; 
				c = a; 
				count = 1; 
			}else{
				count ++; 
			}
		}
		extended += ""+c+count; 
		return extended;
	}
}
