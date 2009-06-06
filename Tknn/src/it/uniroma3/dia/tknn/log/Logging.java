package it.uniroma3.dia.tknn.log;

import org.apache.log4j.Logger;

/**
 * Classe Logging per la configurazione di log4j. Permette l'uso del lo4j
 * in tutte le classi che estendono Logging.
 * @version 	1.0
 * @author L.Rinaldi
 */
public class Logging
{
	protected static Logger log = Logger.getLogger(Logging.class); 
	
	@SuppressWarnings("unchecked")
	public static  Logger getInstance(Class c)
	{
		return  Logger.getLogger(c); 
	}
	
}