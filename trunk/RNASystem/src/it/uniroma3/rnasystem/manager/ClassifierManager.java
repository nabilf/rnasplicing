package it.uniroma3.rnasystem.manager;

import it.uniroma3.rnaclassifier.exception.ClassifierException;
import it.uniroma3.rnaclassifier.main.GenericClassifier;
import it.uniroma3.rnaclassifier.performance.PerformanceManager;
import it.uniroma3.rnaclassifier.performance.contingency.ContingencyMatrixManager;
import it.uniroma3.rnasystem.logging.PerformanceLogger;
import it.uniroma3.rnasystem.model.RNASequence;
import it.uniroma3.rnasystem.setup.RNASystemConfiguration;

import java.util.Set;

import org.apache.log4j.Logger;

public class ClassifierManager {
	
	private static Logger log = Logger.getLogger(ClassifierManager.class); 
	
	public static void classify(Set<RNASequence> trainingSet, Set<RNASequence> testSet) {
		log.info("*****   Start Classification   ******" ); 
		GenericClassifier classifier = null; 
		try {
			classifier = (GenericClassifier) Class.forName(RNASystemConfiguration.classifierClass).newInstance(); 
			ContingencyMatrixManager cm = classifier.execute(trainingSet,testSet);
			//cm.printMatrix(0); 
			PerformanceManager pm = new PerformanceManager(cm.contingency_ei, cm.contingency_ie, cm.contingency_n,cm.microMeasure);
			PerformanceLogger.openFile();
			PerformanceLogger.writeReport(pm.getList(), pm.getCmList()); 
			PerformanceLogger.closeFile(); 
			log.info("*****   Classification Complete   ******" );
			
		} catch (InstantiationException e) {
			log.error("Unable to istantiate "+RNASystemConfiguration.classifierClass); 
			log.error("*****   Classification ERROR   ******" );
		} catch (IllegalAccessException e) {
			log.error("Illegal Access to "+RNASystemConfiguration.classifierClass);
			log.error("*****   Classification ERROR   ******" );
		} catch (ClassNotFoundException e) {
			log.error("Class not found Exception "+RNASystemConfiguration.classifierClass); 
			log.error("*****   Classification ERROR   ******" );
		} catch (ClassifierException e) {
			log.error("Classifier Exception ", e.getCause()); 
			log.error("*****   Classification ERROR   ******" );
		} 
		
	}

}
