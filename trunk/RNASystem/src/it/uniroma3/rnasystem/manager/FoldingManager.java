package it.uniroma3.rnasystem.manager;

import it.uniroma3.rnafolding.exception.RNAFoldingException;
import it.uniroma3.rnafolding.main.RNAFolding;
import it.uniroma3.rnasystem.exception.CacheException;
import it.uniroma3.rnasystem.model.RNAClass;
import it.uniroma3.rnasystem.model.RNASequence;
import it.uniroma3.rnasystem.setup.RNASystemConfiguration;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class FoldingManager {
	private static Logger log = Logger.getLogger(FoldingManager.class); 
	
	public static Map<RNAClass, List<RNASequence>> fold(Map<RNAClass, List<RNASequence>> dataset){
		log.info("*****   Start Folding   ******" ); 
		try {
	
			if(!RNASystemConfiguration.useFoldingCache){
				log.info("Compute Folding...");
				FoldingManager.foldAndCache(dataset); 
				 
			}else{
				log.info("loading folding from cache ");
				dataset = CacheManager.loadMapCache(RNASystemConfiguration.dataSetPath+RNASystemConfiguration.foldingDataset); 
				 
			}
		} catch (CacheException e) {
			log.error("Cache Exception : "+e.getMessage());
			log.info("Recompute Cache...");
			FoldingManager.foldAndCache(dataset); 
			
		} 
		log.info("*****   Folding Complete   ******" );
		return dataset; 
	}
	private static void foldAndCache(Map<RNAClass, List<RNASequence>> dataset){
	
		try {			

			List<String> classes = new LinkedList<String>();
			
			Set<RNAClass> keySet = dataset.keySet(); 
			Iterator<RNAClass> it = keySet.iterator(); 
			
			//FoldingAlgorithmEnum fa = FoldingAlgorithmEnum.getRNAFolding(ConfigurationLoader.get(Constant.FOLDING_ALGORITHM)); 
			while(it.hasNext()){
				RNAClass key = it.next();
				List<RNASequence> rnaList = dataset.get(key); 

				for (RNASequence sequence : rnaList) {
					sequence.setStructure(RNAFolding.fold(	sequence.getSequence(), 
															RNASystemConfiguration.foldingClass, 
															RNASystemConfiguration.cacheDatasetPath+key.toString(), 
															key.toString())); 
				}
				
				classes.add(RNASystemConfiguration.cacheDatasetPath+key.toString());  
			}
			RNAFolding.mergeCache(RNASystemConfiguration.dataSetPath+RNASystemConfiguration.foldingDataset, classes); 
		} catch (RNAFoldingException e) {
			log.error("Folding Exception" , e); 
		} 
		
	}
}
