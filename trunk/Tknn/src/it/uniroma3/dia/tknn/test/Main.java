package it.uniroma3.dia.tknn.test;

import it.uniroma3.dia.tknn.log.ArffFileCreator;
import it.uniroma3.dia.tknn.log.Logging;
import it.uniroma3.dia.tknn.main.ThreadManager;
import it.uniroma3.dia.tknn.model.Element;
import it.uniroma3.dia.tknn.setup.DataSetReader;
import it.uniroma3.dia.tknn.setup.SysSetup;

import java.util.Stack;

import org.apache.log4j.Logger;


public class Main {
	public static Logger log = Logging.getInstance(Main.class); 
	
	
	public static void main(String[] args) {
		try {
			SysSetup.init();
//			ArffFileCreator.openFile("training.arff"); 
//			ArffFileCreator.writeFile(DataSetReader.trainingSet); 
//			ArffFileCreator.closeFile(); 
//
//			ArffFileCreator.openFile("test.arff"); 
//			ArffFileCreator.writeFile(DataSetReader.testSet); 
//			ArffFileCreator.closeFile(); 
			
			ThreadManager man = new ThreadManager(); 
			man.start(); 
		
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	
	public static void printMatrix(double[][] matrix, String sequence){
		log.debug("Nussinov MATRIX ");
		System.out.print("\t"); 
	    
	    for (int j = 0; j < sequence.length(); j++) 
	    	System.out.print(sequence.charAt(j)+"\t"); 
	    
	    System.out.println("\n"); 
	    
	    for (int i = 0; i < sequence.length(); i++)
	    { 
	    	System.out.print(sequence.charAt(i)+"\t"); 
	    	for (int j = 0; j < i-1; j++)
	    		System.out.print("X\t"); 
	    	for (int j = i-1; j < sequence.length(); j++){ 
	    		if (j >= 0) System.out.print(matrix[i][j]+"\t"); 
	     } 
	     System.out.println("\n"); 
	    }
	}
	
	public static void printStructure(Stack<Element> basePairStack, String sequence){
	    while (!basePairStack.empty()){ 
	        Element e = (Element)basePairStack.pop(); 
	        System.out.println(sequence.charAt(e.getI())+""+(e.getI()+1)+"-"+sequence.charAt(e.getJ())+""+(e.getJ()+1)); 
	       } 		
	}
}
