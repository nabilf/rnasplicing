package it.uniroma3.rnasystem.utils;

import it.uniroma3.rnafolding.model.FoldingAlgorithmEnum;
import it.uniroma3.rnasystem.model.RNAClass;

public class SwitchEnum {

	public static FoldingAlgorithmEnum getFoldingAlgorithm(String name){
		if(name.equals(FoldingAlgorithmEnum.NUSSINOV.toString()))
			return FoldingAlgorithmEnum.NUSSINOV; 
		else if(name.equals(FoldingAlgorithmEnum.NUSSINOV_JACOBSEN.toString()))
			return FoldingAlgorithmEnum.NUSSINOV; 
		else
			return FoldingAlgorithmEnum.NUSSINOV; 
	}
	
	public static RNAClass getRNAClass(String name){
		if(name.equals(RNAClass.EI.toString()))
			return RNAClass.EI; 
		else if(name.equals(RNAClass.IE.toString()))
			return RNAClass.IE; 
		else if(name.equals(RNAClass.N.toString()))
			return RNAClass.N; 
		else
			return RNAClass.NONE; 
	}
}
