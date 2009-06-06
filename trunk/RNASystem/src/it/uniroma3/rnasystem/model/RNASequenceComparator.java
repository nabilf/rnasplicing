package it.uniroma3.rnasystem.model;

import it.uniroma3.rnasystem.constant.Constant;

import java.util.Comparator;


public class RNASequenceComparator implements Comparator<RNASequence>{
	int compareType = 0; 
	
	public RNASequenceComparator(int compareType){
		this.compareType = compareType; 
	}
	public int compare(RNASequence o1, RNASequence o2) {
		
		int compare = 0; 
		switch (compareType){
		case Constant.SEQUENCE_COMPARE_K: 
			compare =  (new Double(o1.getDistance())).compareTo(new Double(o2.getDistance()));  
			break; 
		case Constant.SEQUENCE_COMPARE_CLASS: 
			compare = ((o1.getRealClass())).compareTo((o2.getRealClass())); 
			break; 
		}
		return compare; 
	}
}
