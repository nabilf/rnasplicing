package it.uniroma3.rnasystem.xmlconfig;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XMLParser {

	private static Document xml; 
	private static Element rootElement; 
	
   	private static Logger log = Logger.getLogger(XMLParser.class); 
    /**
     * Metodo di Inizializzazione del parser
     * Effettua il parse del file xml presente nel path passato come parametro
     * ed effettua l'inizializzazione delle variabili Document e Root Element
     * @param path
     */
	public static void init(String path) 
   	{
		log.debug("Inizializzazione del Parser di configurazione del Controller"); 
		File f = new File(path);
		
		DocumentBuilderFactory factory = null; 
		DocumentBuilder builder = null;
		
		try {
			factory = DocumentBuilderFactory.newInstance(); 
			builder = factory.newDocumentBuilder();
			//builder.
			xml = builder.parse(f);
			rootElement = xml.getDocumentElement(); 
			log.debug("Parse Effettuato"); 
		} catch (ParserConfigurationException e) {
			log.fatal("Errore nel parsing del file xml di configurazione del controller"); 
			e.printStackTrace();
		} catch (SAXException e) {
			log.fatal("Errore SAX"); 
			e.printStackTrace();
		} catch (IOException e) {
			log.fatal("Errore di lettura del file XML"); 
			e.printStackTrace();
		} 
		
		log.info("Parsing XML Completato "); 
	}
	

}
