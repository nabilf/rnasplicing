package it.uniroma3.dia.tknn.config;

import it.uniroma3.dia.tknn.log.Logging;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


import org.apache.log4j.Logger;

public class ConfigurationLoader {
	public static Logger log = Logging.getInstance(ConfigurationLoader.class);  
	
	protected static Properties cfg; 
	private static String propPath = "";
	private static boolean propChanged = false;

	public static void setPropPath(String newPropPath){
		log.debug("Setting Property Configuration File Path "+newPropPath); 
		propPath = newPropPath;
		propChanged = true;
	}
	
	public static String getPropPath(){
		propChanged = false;		
		return propPath;
	}
	
	public static synchronized String get(String prop) throws IOException{
		log.debug("Getting property for "+prop);
		if(cfg==null || propChanged){
			cfg=new Properties(); 
			cfg.load(new FileInputStream(getPropPath()));
		}
		if(cfg.getProperty(prop)==null) { 
			throw new RuntimeException(prop+": Voce di configurazione assente");
		}	
		return cfg.getProperty(prop); 
	}
	
	public static synchronized void setProperty(String key, String value){
		log.debug("Setting "+key+" property "); 
		cfg.setProperty(key, value); 
	}
}
