package it.uniroma3.rnakernels.models;

import it.uniroma3.rnakernels.constant.Constant;

import java.util.Comparator;

public class ElementComparator implements Comparator<Element>{
	
	public int compareType; 
	
	public ElementComparator(int compareType){
		this.compareType = compareType; 
	}
	public int compare(Element o1, Element o2) {
		
		int compare = 0; 
		switch (compareType){
		case Constant.COMPARE_I: 
			compare =  (new Integer(o1.getI())).compareTo(new Integer(o2.getI())); 
			break; 
		case Constant.COMPARE_J: 
			compare = (new Integer(o1.getJ())).compareTo(new Integer(o2.getJ())); 
			break; 
		case Constant.COMPARE_LEVEL: 
			//Ordinamento decrescente. Dal più grande al più piccolo, perchè i livelli sono negativi. 
			compare =  (new Integer(o2.getLevel())).compareTo(new Integer(o1.getLevel()));
			break; 
		}
		return compare; 
	}


}
