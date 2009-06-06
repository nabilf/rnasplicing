package it.uniroma3.rnasystem.xmlconfig;

import java.io.*;

import org.xml.sax.SAXException;
import javax.xml.validation.*; 
import javax.xml.transform.*; 
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.*;

public class XMLValidator{

	private static Logger log = Logger.getLogger(XMLValidator.class); 
	
    public static void validate(String xsdPath, File xml) throws SAXException, IOException {

        // 1. Lookup a factory for the W3C XML Schema language
        SchemaFactory factory = 
            SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        
        // 2. Compile the schema. 
        // Here the schema is loaded from a java.io.File, but you could use 
        // a java.net.URL or a javax.xml.transform.Source instead.
        File schemaLocation = new File(xsdPath);
        Schema schema = factory.newSchema(schemaLocation);
    
        // 3. Get a validator from the schema.
        Validator validator = schema.newValidator();
        
        // 4. Parse the document you want to check.
        Source source = new StreamSource(xml);
        
        // 5. Check the document
        try {
            validator.validate(source);
           log.info(xml.getName() + " is valid.");
        }
        catch (SAXException ex) {
            log.error(xml.getName() + " is not valid because ");
            log.error(ex.getMessage());
        }  
        
    }

    
}
