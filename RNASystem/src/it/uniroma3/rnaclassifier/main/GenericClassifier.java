package it.uniroma3.rnaclassifier.main;

import it.uniroma3.rnaclassifier.exception.ClassifierException;
import it.uniroma3.rnaclassifier.model.ClassifierInput;
import it.uniroma3.rnaclassifier.model.ClassifierOutput;
import it.uniroma3.rnaclassifier.performance.contingency.ContingencyMatrixManager;
import it.uniroma3.rnasystem.model.RNASequence;

import java.util.Set;

import org.apache.log4j.Logger;
/**
 * 
 * @author 
 * @see: Pattern Template Method 
 */

public abstract class GenericClassifier {
 private static Logger log = Logger.getLogger(GenericClassifier.class); 
	
	public final ContingencyMatrixManager execute (Set<RNASequence> trainingSet, Set<RNASequence> testSet) throws ClassifierException {
		log.debug("Training Set Size" +trainingSet.size()); 
		setup(trainingSet, testSet); 
		return work(trainingSet, testSet);  
	} 

	/***** PRIMITIVE OPERATION *****/ 
	/*Devono essere personalizzate nelle sottoclassi */
	protected abstract void setup(Set<RNASequence> trainingSet, Set<RNASequence> testSet)throws ClassifierException;
	protected abstract ContingencyMatrixManager work(Set<RNASequence> trainingSet, Set<RNASequence> testSet) throws ClassifierException;
}
