package it.uniroma3.rnakernels.main;

import it.uniroma3.rnakernels.builders.Builder;
import it.uniroma3.rnakernels.exception.RNABuilderException;
import it.uniroma3.rnakernels.exception.RNAKernelException;
import it.uniroma3.rnakernels.models.Node;
import it.uniroma3.rnakernels.models.RNAStructureEnum;
import it.uniroma3.rnasystem.setup.RNASystemSetup;

import java.util.List;

import org.apache.log4j.Logger;

public class RNABuilder {

	private static Logger log = Logger.getLogger(RNABuilder.class); 
	//TODO: Aggiungere switch metrica
	public static List<Node> buildStructure(String buildingClass, String sequence, String dotBracketStructure ) throws RNAKernelException{
		List<Node> rnaStructure = null; 
		try {
			Builder  builder = (Builder) Class.forName(buildingClass).newInstance();
			builder.setSequence(sequence); 
			builder.setStructure(dotBracketStructure); 
			rnaStructure = builder.execute(); 
		} catch (InstantiationException e) {
			log.error("Unable to istantiate "+buildingClass+" class", e);
			throw new RNAKernelException(e.getMessage()); 
		} catch (IllegalAccessException e) {
			log.error("Unable to access "+buildingClass+" class");
			throw new RNAKernelException(e.getMessage()); 		
		} catch (ClassNotFoundException e) {
			log.error("Class "+buildingClass+" not found");
			throw new RNAKernelException(e.getMessage()); 
		} catch (RNABuilderException e) {
			log.error("Cannot build structure "+buildingClass.toString());
			throw new RNAKernelException(e.getMessage()); 
		}   
		return rnaStructure; 
		
		
	}
}
