package it.uniroma3.rnaclassifier.paum.models;

import it.uniroma3.rnaclassifier.model.ClassifierInput;


public class PAUMInput extends ClassifierInput { 
	
	//SparseFeatureVector [] dataLearning, int totalNumFeatures, short [] classLabels, int numTraining
	private SparseFeatureVector[] dataLearning; 
	private int totNumFeatures; 
	private short[] classLabels; 
	private int numTraining;
	
	public PAUMInput(short[] classLabels, int numTraining, int totNumFeatures, SparseFeatureVector[] dataLearning) {
		this.classLabels = classLabels;
		this.numTraining = numTraining;
		this.totNumFeatures = totNumFeatures;
		this.dataLearning = dataLearning; 
	}
	
	public SparseFeatureVector[] getDataLearning() {
		return dataLearning;
	}

	public void setDataLearning(SparseFeatureVector[] dataLearning) {
		this.dataLearning = dataLearning;
	}

	public int getTotNumFeatures() {
		return totNumFeatures;
	}

	public void setTotNumFeatures(int totNumFeatures) {
		this.totNumFeatures = totNumFeatures;
	}

	public short[] getClassLabels() {
		return classLabels;
	}

	public void setClassLabels(short[] classLabels) {
		this.classLabels = classLabels;
	}

	public int getNumTraining() {
		return numTraining;
	}

	public void setNumTraining(int numTraining) {
		this.numTraining = numTraining;
	} 	
}
