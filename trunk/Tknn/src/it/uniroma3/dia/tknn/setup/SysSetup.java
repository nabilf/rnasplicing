package it.uniroma3.dia.tknn.setup;

import it.uniroma3.dia.tknn.config.ConfigurationLoader;
import it.uniroma3.dia.tknn.constant.Constant;
import it.uniroma3.dia.tknn.log.Logging;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class SysSetup {
	private static boolean initDone = false;
	public static Logger log = Logging.getInstance(SysSetup.class); 
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
				
				DataSetManager.init(); 
				log.info("[Setup - init] - DataSet Creati");

				
				log.info("[Setup - init] Inizializzazione completata");
			}catch(Throwable t){
				log.fatal("Setup is not completed! cannot run process ", t); 
			}
			initDone = true;
		}
	}
}
