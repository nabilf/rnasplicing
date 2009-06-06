package it.uniroma3.dia.tknn.classifier.methods;

import org.apache.log4j.Logger;

import it.uniroma3.dia.tknn.model.PaumOutput;
import it.uniroma3.dia.tknn.model.SparseFeatureVector;

public class PaumClassifier

{

	private float tauP = 0.1f;

	private float tauN =1;

	private float optB = 0;

	final private int totalLoops = 2000;

	final private float lambInc = (float)0.001;

	

	public PaumClassifier(float tauP, float tauN)

	{

		this.tauP = tauP;

		this.tauN = tauN;

	}

	

	public PaumOutput training(SparseFeatureVector [] dataLearning, int totalNumFeatures, short [] classLabels, int numTraining)

	{

		if(tauN>0)

			tauN = -tauN;

		

		float [] lambs = new float[numTraining];

		float b=0;

		float [] w = new float[totalNumFeatures];

		boolean isModified;

		

		for(int iLoop=0; iLoop<this.totalLoops; ++iLoop)

		{

			isModified  = false;

			for(int iCounter=0; iCounter<numTraining; ++iCounter)

			{

				final int i = iCounter;

				double sum =0;

				int len = dataLearning[i].getLen();

				int [] indexes = dataLearning[i].getIndexes();

				float [] values = dataLearning[i].getValues();

				

				for(int j1=0; j1<len; ++j1)
					//Polinomio al kernel 
					//Polinomial learning machine: spazi non linearmente separabili
					//sum += Math.pow((w[dataLearning[i].indexes[j1]]*dataLearning[i].values[j1])+1, 2);
					sum += w[dataLearning[i].indexes[j1]]*dataLearning[i].values[j1];


				sum += b;

				sum += lambs[i];

				short y00 = classLabels[i];
				
				if(y00>0 && sum<=tauP)

				{

					for(int j1=0; j1<len; ++j1)

						w[dataLearning[i].indexes[j1]] += dataLearning[i].values[j1];

					lambs[i] += lambInc;

					b += 1;

					isModified = true;

				} 

				else if(y00<0 && sum>=tauN)

				{

					for(int j1=0; j1<len; ++j1)

						w[dataLearning[i].indexes[j1]] -= dataLearning[i].values[j1];

					lambs[i] -= lambInc;

					b -= 1;

					isModified = true;

				}

			}

			if(!isModified)

				break;

		}
		PaumOutput pout = new PaumOutput(b, w); 
		

		/*System.out.println(b);

		for (int k=0; k<w.length; k++)

			System.out.println(w[k]);

		System.out.println(totalNumFeatures);*/
		return pout; 
	}

	
}
