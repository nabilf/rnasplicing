package it.uniroma3.rnasystem.model;

import it.uniroma3.rnasystem.constant.Constant;

import java.util.Comparator;

public class RNAComparator implements Comparator<RNASequence>{

	int compareType = 0; 
	
	public RNAComparator(int compareType){
		this.compareType = compareType; 
	}
	public int compare(RNASequence o1, RNASequence o2) {
		
		int compare = 0; 
		switch (compareType){
		case Constant.COMPARE_PREDICTED_CLASS: 
			compare =  ((o1.getPredictedClass())).compareTo((o2.getPredictedClass()));  
			break; 
		case Constant.COMPARE_REAL_CLASS: 
			compare = ((o1.getRealClass())).compareTo((o2.getRealClass())); 
			break; 
		}
		return compare; 
	}
	
}
