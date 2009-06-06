package it.uniroma3.rnaclassifier.paum.algorithm;

import it.uniroma3.rnaclassifier.paum.exception.PAUMException;
import it.uniroma3.rnaclassifier.paum.exception.PAUMInputException;
import it.uniroma3.rnaclassifier.paum.models.PAUMInput;
import it.uniroma3.rnaclassifier.paum.models.PAUMOutput;

public class Paum
{

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

		if(tauN>0)
			tauN = -tauN;

		float[] lambs = null; 
		if(in.getNumTraining() <= 0){
			throw new PAUMInputException("numTraining", ""+in.getNumTraining()); 
		}else{
			lambs = new float[in.getNumTraining()];
		}
		
		
		float b=0;

		
		float [] w = null; 
		if(in.getTotNumFeatures() <= 0){
			throw new PAUMInputException("totNumFeatures", ""+in.getTotNumFeatures()); 
		}else{
			w = new float[in.getTotNumFeatures()];
		}
		
		boolean isModified;

		try{
			for(int iLoop=0; iLoop<this.totalLoops; ++iLoop){
	
				isModified  = false;
	
				for(int iCounter=0; iCounter<in.getNumTraining(); ++iCounter){
	
					final int i = iCounter;
	
					double sum =0;
					
					if(in.getDataLearning() != null){
						
						int len =in.getDataLearning().length;
		
						//Polinomio al kernel 
						//Polinomial learning machine: spazi non linearmente separabili
						//sum += Math.pow((w[dataLearning[i].indexes[j1]]*dataLearning[i].values[j1])+1, 2);
						for(int j1=0; j1<len; ++j1)
							sum += w[in.getDataLearning()[i].indexes[j1]]*in.getDataLearning()[i].values[j1];
		
		
						sum += b;
		
						sum += lambs[i];
		
						short y00 = in.getClassLabels()[i];
						
						if(y00>0 && sum<=tauP){
		
							for(int j1=0; j1<len; ++j1)
		
								w[in.getDataLearning()[i].indexes[j1]] += in.getDataLearning()[i].values[j1];
		
							lambs[i] += eta;
		
							b += 1;
		
							isModified = true;
		
						} 
		
						else if(y00<0 && sum>=tauN){
		
							for(int j1=0; j1<len; ++j1)
								w[in.getDataLearning()[i].indexes[j1]] -= in.getDataLearning()[i].values[j1];
		
							lambs[i] -= eta;
		
							b -= 1;
		
							isModified = true;
		
						}
					}else{
						throw new PAUMInputException("dataLearning"); 
					}
				}
		
				if(!isModified)
		
					break;
	
			}
		}catch(Exception t ){
			t.printStackTrace(); 
			throw new PAUMException(t.getMessage()); 
		}
		
		PAUMOutput pout = new PAUMOutput(b, w); 
		
		return pout; 
	}

	
}
