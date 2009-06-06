package it.uniroma3.rnafolding.model;


public enum FoldingAlgorithmEnum {

	NONE, NUSSINOV, NUSSINOV_JACOBSEN; 
	
	public static FoldingAlgorithmEnum getRNAFolding(String folding){
		if(folding.equalsIgnoreCase(FoldingAlgorithmEnum.NUSSINOV.toString())) {
			return FoldingAlgorithmEnum.NUSSINOV;
		} else if(folding.equalsIgnoreCase(FoldingAlgorithmEnum.NUSSINOV_JACOBSEN.toString()))
			return FoldingAlgorithmEnum.NUSSINOV_JACOBSEN; 
		else
			return FoldingAlgorithmEnum.NONE; 
	}
}
