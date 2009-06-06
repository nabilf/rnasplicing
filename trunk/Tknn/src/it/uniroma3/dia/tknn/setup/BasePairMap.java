package it.uniroma3.dia.tknn.setup;

import it.uniroma3.dia.tknn.config.ConfigurationLoader;
import it.uniroma3.dia.tknn.constant.Constant;
import it.uniroma3.dia.tknn.model.BasePair;
import it.uniroma3.dia.tknn.model.BasePairEnum;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BasePairMap extends Object {

	public static Map<String, BasePair> map; 
	
	public static void init(){
			
			map = new HashMap<String, BasePair>(); 
			
			String key = "AU"; 
			map.put(key, createBasePair(key, BasePairEnum.AU, Constant.AU_PROB)); 

			key = "UA"; 
			map.put(key, createBasePair(key, BasePairEnum.UA, Constant.AU_PROB)); 

			key = "AT"; 
			map.put(key, createBasePair(key, BasePairEnum.AT, Constant.AU_PROB)); 

			key = "TA"; 
			map.put(key, createBasePair(key, BasePairEnum.TA, Constant.AU_PROB));
			
			key = "CG"; 
			map.put(key, createBasePair(key, BasePairEnum.CG, Constant.GC_PROB)); 
			
			key= "GC"; 
			map.put(key, createBasePair(key, BasePairEnum.GC, Constant.GC_PROB)); 
			
			key = "GU"; 
			map.put(key, createBasePair(key, BasePairEnum.GU, Constant.GU_PROB)); 
			
			key = "UG"; 
			map.put(key, createBasePair(key, BasePairEnum.UG, Constant.GU_PROB)); 
 
			key = "GT"; 
			map.put(key, createBasePair(key, BasePairEnum.GT, Constant.GT_PROB)); 
			
			key = "TG"; 
			map.put(key, createBasePair(key, BasePairEnum.TG, Constant.GT_PROB)); 
	}
	
	private static BasePair createBasePair(String key, BasePairEnum en, String constant ){
		BasePair pair = new BasePair(); 
		try {
			pair.setConversion(ConfigurationLoader.get(en.toString()));
			pair.setBases(key); 
			pair.setProb(Double.valueOf(ConfigurationLoader.get(constant))); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pair; 
	}
}
