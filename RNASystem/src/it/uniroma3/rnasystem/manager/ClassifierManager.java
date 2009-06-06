package it.uniroma3.rnasystem.manager;

import it.uniroma3.rnaclassifier.exception.ClassifierException;
import it.uniroma3.rnaclassifier.main.GenericClassifier;
import it.uniroma3.rnaclassifier.model.ClassifierEnum;
import it.uniroma3.rnaclassifier.model.ClassifierInput;
import it.uniroma3.rnaclassifier.paum.models.PAUMSetup;
import it.uniroma3.rnasystem.constant.Constant;
import it.uniroma3.rnasystem.model.RNASequence;
import it.uniroma3.rnasystem.setup.ConfigurationLoader;
import it.uniroma3.rnasystem.setup.RNASystemConfiguration;

import java.io.IOException;
import java.util.Set;

public class ClassifierManager {
	
	
	public static void classify(Set<RNASequence> trainingSet, Set<RNASequence> testSet) {
		GenericClassifier classifier = null; 
		try {
			classifier = (GenericClassifier) Class.forName(RNASystemConfiguration.classifierClass).newInstance(); 
			classifier.execute(trainingSet,testSet);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassifierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
