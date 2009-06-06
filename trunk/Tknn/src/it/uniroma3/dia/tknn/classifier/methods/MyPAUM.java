package it.uniroma3.dia.tknn.classifier.methods;

import it.uniroma3.dia.tknn.model.PaumOutput;
import it.uniroma3.dia.tknn.model.SparseFeatureVector;

import org.apache.log4j.Logger;

public class MyPAUM {
	private static Logger log = Logger.getLogger(MyPAUM.class); 
	
	private float tauP =0.1f;
	private float tauN =1;
	//private float optB = 1;
	
	//MOD 
	private float b; 
	private float [] w; 
	
	final private int totalLoops = 2000;
	final private float eta = (float)0.1; //1.0;
	
	public MyPAUM()
	{
		this.tauP = 50;
		this.tauN = 1;
	}
	
	public PaumOutput training(SparseFeatureVector [] dataLearning, int totalNumFeatures, short [] classLabels, int numTraining)
	{		
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
		for (int k=0; k<w.length; k++)

			System.out.print(w[k]+" ");
		System.out.println();
		
		System.out.println("B "+b);
		PaumOutput pout = new PaumOutput(b,w); 
		return pout; 
	}
}
