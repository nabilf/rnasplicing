package it.uniroma3.rnaclassifier.model;


public enum ClassifierEnum {

	NONE, Knn, Paum; 
	
	public static ClassifierEnum getClassifier(String cType){
		if(cType.equalsIgnoreCase(ClassifierEnum.Paum.toString())) {
			return ClassifierEnum.Paum;
		} else if(cType.equalsIgnoreCase(ClassifierEnum.Knn.toString()))
			return ClassifierEnum.Knn; 
		else
			return ClassifierEnum.NONE; 
	}
	
}
