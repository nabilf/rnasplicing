package it.uniroma3.rnasystem.manager;

import it.uniroma3.rnakernels.exception.RNAKernelException;
import it.uniroma3.rnakernels.main.RNABuilder;
import it.uniroma3.rnakernels.models.RNAStructureEnum;
import it.uniroma3.rnasystem.constant.Constant;
import it.uniroma3.rnasystem.exception.CacheException;
import it.uniroma3.rnasystem.model.RNAClass;
import it.uniroma3.rnasystem.model.RNASequence;
import it.uniroma3.rnasystem.setup.ConfigurationLoader;
import it.uniroma3.rnasystem.setup.RNASystemConfiguration;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class BuilderManager {

	private static Logger log = Logger.getLogger(BuilderManager.class); 
	
	public static Map<RNAClass, List<RNASequence>> build(Map<RNAClass, List<RNASequence>> dataset) {	
		log.info("*****    Start Building Structure   *****"); 
		try {
			if(!RNASystemConfiguration.useBuildingCache){
				log.info("Start Building...");
				BuilderManager.buildAndCache(dataset); 
				 
			}else{
				log.info("loading building from cache");
				dataset = CacheManager.readCache(RNASystemConfiguration.buildingDatasetPath, dataset ); 
				 
			}
		} catch (CacheException e) {
			log.error("Cache Exception : "+e.getMessage());
			log.info("Recompute Cache...");
			BuilderManager.buildAndCache(dataset); 
			 
		} 
		log.info("*****    Start Building Structure   *****"); 
		return dataset; 
	}
	private static void buildAndCache(Map<RNAClass, List<RNASequence>> dataset) {
		Set<RNAClass> keySet = dataset.keySet(); 
		Iterator<RNAClass> it = keySet.iterator(); 
		
		try {
			String source = RNASystemConfiguration.buildingDatasetPath;
	
			while(it.hasNext()){
				RNAClass key = it.next(); 
				List<RNASequence> list = dataset.get(key);
				 
				for (RNASequence sequence : list) {					
					sequence.setGraph(RNABuilder.buildStructure(
													RNASystemConfiguration.BuildingClass,
													sequence.getSequence(), 
													sequence.getStructure()));
				}
				CacheManager.writeGraphCache(list,source,key.toString().trim()+".txt"); 
				
			}
		} catch (RNAKernelException e) {
			log.error("Builder Exception" , e); 
		} 

		
	}
	

	
}
