package it.uniroma3.rnaclassifier.paum.algorithm;

import it.uniroma3.rnaclassifier.paum.exception.PAUMException;
import it.uniroma3.rnaclassifier.paum.models.PAUMInput;
import it.uniroma3.rnaclassifier.paum.models.PAUMOutput;
import it.uniroma3.rnaclassifier.paum.models.SparseFeatureVector;

import org.apache.log4j.Logger;

public class Paum
{

	private static Logger log = Logger.getLogger(Paum.class);
	
	private float tauP;

	private float tauN;

	private int totalLoops;

	private float eta;


	/**
	 * Costruttore parametrico
	 * @param eta
	 * @param tauN
	 * @param tauP
	 * @param totalLoops
	 */
	public Paum(float eta, float tauN, float tauP, int totalLoops) {
		super();
		this.eta = eta;
		this.tauN = tauN;
		this.tauP = tauP;
		this.totalLoops = totalLoops;
	}

	/**
	 * Costruttore PAUM con margini parametrici
	 * @param tauP = margine positivo
	 * @param tauN = margine negativo
	 * 
	 * Valori preimpostati
	 * totalLoops = 2000
	 * eta = 0.01
	 */
	public Paum(float tauP, float tauN){
		this.tauP = tauP;
		this.tauN = tauN;
		this.totalLoops = 2000; 
		this.eta = 0.01f;
	}

	/**
	 * Costruttore PAUM con margini ed eta parametrici
	 * @param tauP = margine positivo
	 * @param tauN = margine negativo
	 * @param eta = fattore di apprendimento
	 * 
	 * Valori preimpostati
	 * totalLoops = 2000
	 */
	public Paum(float tauP, float tauN, float eta){
		this.tauP = tauP;
		this.tauN = tauN;
		this.totalLoops = 2000; 
		this.eta = eta;
	}
	
	/**
	 * Costruttore PAUM preimpostato
	 * 
	 * Valori preimpostati
	 * tauP = 0.1
	 * tauN = 1; 
	 * totalLoops = 2000
	 * eta = 0.01
	 */
	public Paum(){
		this.tauP = 0.1f; 
		this.tauN = 1; 
		this.totalLoops = 2000; 
		this.eta = 0.01f; 
	}
	
	public PAUMOutput training(PAUMInput in) throws PAUMException{
		int totalNumFeatures = in.getTotNumFeatures(); 
		SparseFeatureVector[] dataLearning = in.getDataLearning(); 
		int numTraining = in.getNumTraining(); 
		short[] classLabels = in.getClassLabels(); 
		
		if(tauN>0)
			tauN = -tauN;
		
		float [] w = new float[totalNumFeatures];
		float b=0;
		
		for(int iLoop=0; iLoop<this.totalLoops; ++iLoop)
		{
			boolean notModified = true; 
			int i = 0; 
			while(notModified && i<numTraining){
				int [] indexes = dataLearning[i].getIndexes();
				float [] values = dataLearning[i].getValues();
				
				
				//Calcolo di R^2
				float R = 0;
				for(int v =0; v<values.length; v++){
					R += Math.pow(values[v], 2); 
				}
				R = (float) Math.sqrt(R); 
				
				//Prodotto vettoriale <wi, xi>
				float vectSum = 0; 
				for(int j =0; j<totalNumFeatures; j++){
					vectSum += w[j]*values[j]; 
				}
				
				//Vettore yi(<wi,xi>+b)
				float vector = classLabels[i]*(vectSum+b); 
				
				//if yi(<wi,xi>+b) <= tau(yi)
				if(vector <= tauP || vector <= tauN){
					for(int n =0;n<totalNumFeatures; n++){
						w[dataLearning[i].indexes[n]] += (((dataLearning[i].values[n])*eta)*classLabels[i]);
						b += eta*classLabels[i]*(Math.pow(R, 2)); 
						notModified = false; 
					}
				}
				
				i++;
			}
		}
//		for (int k=0; k<w.length; k++)
//
//			System.out.print(w[k]+" ");
//		System.out.println();
//		
//		System.out.println("B "+b);
		PAUMOutput pout = new PAUMOutput(b,w); 
		return pout; 
	}	
}
