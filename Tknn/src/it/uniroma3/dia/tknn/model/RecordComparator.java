package it.uniroma3.dia.tknn.model;

import java.util.Comparator;

import it.uniroma3.dia.tknn.constant.Constant;

public class RecordComparator implements Comparator<Record>{

	int compareType = 0; 
	
	public RecordComparator(int compareType){
		this.compareType = compareType; 
	}
	public int compare(Record o1, Record o2) {
		
		int compare = 0; 
		switch (compareType){
		case Constant.COMPARE_K: 
			compare =  (new Double(o1.getK())).compareTo(new Double(o2.getK()));  
			break; 
		case Constant.COMPARE_CLASS: 
			compare = ((o1.getClassName())).compareTo((o2.getClassName())); 
			break; 
		}
		return compare; 
	}
	
}
