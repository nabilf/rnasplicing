package it.uniroma3.rnasystem.setup;

import it.uniroma3.rnakernels.models.RNAStructureEnum;
import it.uniroma3.rnakernels.setup.BasePairMap;
import it.uniroma3.rnasystem.constant.Constant;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class RNASystemSetup {
	private static boolean initDone = false;
	public static Logger log = Logger.getLogger(RNASystemSetup.class); 
	public static Map<RNAStructureEnum, String> classMap; 
	
	public static void init() {
		if(!initDone) {
			try{
				//Inizializzazione Log4j
				DOMConfigurator.configure(Constant.LOG4J_INIT); 		
				log.info("[Setup - init] - log4j Configured"); 
				
				ConfigurationLoader.setPropPath(Constant.PROPERTIES_PATH); 
				log.info("[Setup - init] - Property File Configured"); 
				
				BasePairMap.init(); 
				log.info("[Setup - init] - Base Pair Map creata");
				
				RNASystemSetup.createMap(); 
				log.info("[Setup-init] - Map create "+classMap.size()); 
				
				log.info("[Setup - init] Inizializzazione completata");
			}catch(Throwable t){
				log.fatal("Setup is not completed! cannot run process ", t); 
			}
			initDone = true;
		}
	}
	
	private static void createMap(){
		if(RNASystemSetup.classMap == null){
			RNASystemSetup.classMap = new HashMap<RNAStructureEnum, String>(); 
			RNASystemSetup.classMap.put(RNAStructureEnum.RNATree,"it.uniroma3.rnakernels.algorithm.TreeBuilder"); 
			RNASystemSetup.classMap.put(RNAStructureEnum.RNAGraph,"it.uniroma3.rnakernels.algorithm.GraphBuilder"); 	
		}
	}
}
