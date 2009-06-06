package it.uniroma3.rnakernels.setup;

import it.uniroma3.rnakernels.constant.Constant;
import it.uniroma3.rnakernels.models.BasePair;
import it.uniroma3.rnakernels.models.BasePairEnum;

import java.util.HashMap;
import java.util.Map;

public class BasePairMap extends Object {

	public static Map<String, BasePair> map; 
	
	public static void init(){
			
			map = new HashMap<String, BasePair>(); 
			
			String key = "AU"; 
			String conversion = "X";
			map.put(key, createBasePair(key, BasePairEnum.AU, Constant.AU_PROB, conversion)); 

			key = "UA"; 
			conversion = "X1"; 
			map.put(key, createBasePair(key, BasePairEnum.UA, Constant.AU_PROB, conversion)); 

			key = "AT"; 
			conversion = "Y"; 
			map.put(key, createBasePair(key, BasePairEnum.AT, Constant.AU_PROB, conversion)); 

			key = "TA";
			conversion = "Y1"; 
			map.put(key, createBasePair(key, BasePairEnum.TA, Constant.AU_PROB, conversion));
			
			key = "CG";
			conversion = "W"; 
			map.put(key, createBasePair(key, BasePairEnum.CG, Constant.GC_PROB, conversion)); 
			
			key= "GC"; 
			conversion = "W1"; 
			map.put(key, createBasePair(key, BasePairEnum.GC, Constant.GC_PROB, conversion)); 
			
			key = "GU";
			conversion = "Z";  
			map.put(key, createBasePair(key, BasePairEnum.GU, Constant.GU_PROB, conversion)); 
			
			key = "UG"; 
			conversion = "Z1"; 
			map.put(key, createBasePair(key, BasePairEnum.UG, Constant.GU_PROB, conversion)); 
 
			key = "GT"; 
			conversion = "V"; 
			map.put(key, createBasePair(key, BasePairEnum.GT, Constant.GT_PROB, conversion)); 
			
			key = "TG";
			conversion = "V1"; 
			map.put(key, createBasePair(key, BasePairEnum.TG, Constant.GT_PROB, conversion)); 
	}
	
	private static BasePair createBasePair(String key, BasePairEnum en, String constant, String conversion ){
		BasePair pair = new BasePair(); 
		try {
			pair.setConversion(conversion);
			pair.setBases(key); 
			pair.setProb(1.0); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pair; 
	}


}
